-- 1. News 데이터 삽입
INSERT INTO news (news_id, uuid, title, author, content, views, thumbnail_url, created_at) VALUES
(1, '123e4567-e89b-12d3-a456-426614174000', 'AI가 바꿀 미래 사회', '홍길동', 'AI는 산업 전반에 혁신을...', 120, 'https://example.com/thumb1.jpg', CURRENT_TIMESTAMP),
(2, '223e4567-e89b-12d3-a456-426614174001', '2025년 경제 전망', '김영희', '2025년 경제는 완만한 회복세...', 89, 'https://example.com/thumb2.jpg', CURRENT_TIMESTAMP),
(3, '323e4567-e89b-12d3-a456-426614174002', '우주 탐사의 현재와 미래', '이준호', '최근 우주 탐사 미션이 활발히...', 234, 'https://example.com/thumb3.jpg', CURRENT_TIMESTAMP);

-- 2. Domain 데이터 삽입 (각 뉴스에 속한 카테고리)
INSERT INTO domain (domain_id, domain, news_id) VALUES
(1, 'IT', 1),
(2, '기술', 1),
(3, '경제', 2),
(4, '시사', 2),
(5, '우주', 3),
(6, '과학', 3);

-- 3. Word 데이터 삽입 (뉴스 콘텐츠 속 단어 설명)
INSERT INTO word (word_id, word, definition, sentence, level, news_id) VALUES
-- News 1번 관련 단어
(1, '혁신', '새롭고 획기적인 변화', 'AI는 산업 전반에 혁신을 가져왔다.', 'HIGH', 1),
(2, '산업', '경제 활동의 한 분야', 'AI는 여러 산업에 영향을 준다.', 'MIDDLE', 1),

-- News 2번 관련 단어
(3, '경제', '재화의 생산과 소비 활동', '2025년 경제는 회복세를 보일 것이다.', 'MIDDLE', 2),
(4, '회복세', '다시 좋아지는 추세', '경제가 완만한 회복세를 보이고 있다.', 'HIGH', 2),

-- News 3번 관련 단어
(5, '우주', '지구 밖의 공간', '우주 탐사는 미래의 핵심 산업이다.', 'LOW', 3),
(6, '탐사', '자세히 조사하고 살피는 것', '과학자들은 화성을 탐사했다.', 'MIDDLE', 3);
