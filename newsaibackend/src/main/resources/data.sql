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

-- 4. 긍부정 평가 (뉴스별 긍부정 평가)
INSERT INTO pnevaluation (pn_evaluation_id, positive_comment, negative_comment, news_id) VALUES
(1, 'AI의 긍정적 활용 가능성이 돋보입니다.', '기술 발전이 일자리에 미칠 부정적 영향이 우려됩니다.', 1),
(2, '경제 회복에 대한 낙관적인 전망이 제시되었습니다.', '구체적인 지표나 수치가 부족합니다.', 2),
(3, '우주 탐사의 진전이 인상적이며 미래지향적입니다.', '우주 개발에 드는 과도한 비용이 문제로 보입니다.', 3);

-- 5. Summary 데이터 삽입 (뉴스별 요약 - 레벨별 1개씩)
INSERT INTO summary (summary_id, level, summary_content, news_id) VALUES
-- News 1: AI가 바꿀 미래 사회
(1, 'LOW', 'AI는 사회에 영향을 준다.', 1),
(2, 'MIDDLE', 'AI는 다양한 산업에 도입되어 구조를 변화시키고 있다.', 1),
(3, 'HIGH', 'AI 기술은 산업 전반에 걸쳐 자동화와 효율화를 촉진시키며, 미래 사회 구조의 근본적 변화를 이끌고 있다.', 1),

-- News 2: 2025년 경제 전망
(4, 'LOW', '경제는 좋아질 것이다.', 2),
(5, 'MIDDLE', '2025년 경제는 회복 국면에 진입할 것으로 보인다.', 2),
(6, 'HIGH', '전문가들은 2025년 경제가 완만한 회복세를 보일 것으로 전망하며, 소비와 투자의 점진적 증가가 이를 뒷받침할 것으로 보고 있다.', 2),

-- News 3: 우주 탐사의 현재와 미래
(7, 'LOW', '우주 탐사가 활발해지고 있다.', 3),
(8, 'MIDDLE', '우주 탐사는 민간과 정부 차원에서 활발히 이루어지고 있다.', 3),
(9, 'HIGH', '최근 우주 탐사는 기술의 발전과 민간기업의 참여로 더욱 활발해지고 있으며, 장기적으로는 화성 탐사와 같은 미래 지향적 목표를 향하고 있다.', 3);
