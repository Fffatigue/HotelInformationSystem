ALTER TABLE REVIEW
ADD CHECK(score BETWEEN 0 AND 10);