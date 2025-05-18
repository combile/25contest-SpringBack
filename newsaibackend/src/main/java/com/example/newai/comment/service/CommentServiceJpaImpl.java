package com.example.newai.comment.service;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.comment.entity.Comment;
import com.example.newai.comment.repository.CommentRepository;
import com.example.newai.comment.vo.CommentDto;
import com.example.newai.comment.vo.CommentRequest;
import com.example.newai.login.CustomUserDetails;
import com.example.newai.member.entity.Member;
import com.example.newai.member.repository.MemberRepository;
import com.example.newai.news.entity.News;
import com.example.newai.news.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceJpaImpl implements CommentService {
    private final MemberRepository memberRepository;
    private final AppUserRepository appUserRepository;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @Override
    @Transactional
    public CommentDto createComment(Authentication authentication, CommentRequest commentRequest, UUID uuid) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);
        News news = newsRepository.findByUuid(uuid);

        if (authAppUser.isEmpty() || news == null)
            return null;

        AppUser appUser = authAppUser.get();
        Comment comment = new Comment();

        comment.setAppUser(appUser);
        comment.setNews(news);
        comment.setComment(commentRequest.getComment());

        commentRepository.save(comment);

        return commentToCommentDto(comment);
    }

    @Override
    public List<CommentDto> readMyAllComments(Authentication authentication) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        AppUser appUser = authAppUser.get();
        List<Comment> comments = commentRepository.findByAppUser(appUser);

        List<CommentDto> commentDtoList = comments.stream().map(CommentServiceJpaImpl::commentToCommentDto).toList();

        return commentDtoList;
    }

    @Override
    public List<CommentDto> readNewsAllComments(UUID uuid) {
        News news = newsRepository.findByUuid(uuid);

        if (news == null)
            return null;

        List<Comment> comments = commentRepository.findByNews(news);
        List<CommentDto> commentDtoList = comments.stream().map(CommentServiceJpaImpl::commentToCommentDto).toList();

        return commentDtoList;
    }

    @Override
    @Transactional
    public CommentDto updateComment(Authentication authentication, CommentRequest commentRequest) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        Comment comment = commentRepository.findById(commentRequest.getCommentId()).orElse(null);

        if (comment == null)
            return null;

        if (commentRequest.getComment() == null || commentRequest.getComment().isEmpty())
            return null;

        comment.setComment(commentRequest.getComment());
        commentRepository.save(comment);

        return commentToCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Authentication authentication, CommentRequest commentRequest) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return;

        Comment comment = commentRepository.findById(commentRequest.getCommentId()).orElse(null);

        if (comment == null)
            return;

        commentRepository.delete(comment);
    }

    public static CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto(comment.getCommentId(), comment.getComment(), comment.getCreatedAt());
        return commentDto;
    }

    private Optional<AppUser> authenticationToAppUser(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (customUserDetails == null) {
            return Optional.empty();
        }

        Member member = memberRepository.findByLoginId(customUserDetails.getLoginId());
        return appUserRepository.findById(member.getAppUser().getAppUserId());
    }
}
