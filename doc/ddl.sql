drop table if exists tb_event;
CREATE TABLE `tb_event`
(
    `no`               int          NULL,
    `title`            varchar(20)  NULL,
    `content`          text         NULL,
    `writer`           varchar(20)  NULL default '이벤트관리자',
    `event_start_date` datetime     NULL,
    `event_end_date`   datetime     NULL,
    `location`         varchar(200) NULL,
    `price`            int          Null default 0,
    `created_date`     datetime     NULL default now()
);

drop table if exists tb_member;
CREATE TABLE `tb_member`
(
    `no`    int          NULL,
    `name`  varchar(20)  NULL,
    `email` varchar(250) NULL,
    `type`  varchar(1)   null default '2' # 관리자: 1, 일반: 2
);

drop table if exists tb_res;
CREATE TABLE `tb_res`
(
    `no`     int         NULL,
    `bno`    int         NULL,
    `status` varchar(20) NULL,
    `pno`    int         NULL,
    `count`  int         Null default 0,
    `created_date`     datetime     NULL default now()
);


-- 기본키세팅
ALTER TABLE `tb_event`
    ADD CONSTRAINT `PK_TB_EVENT` PRIMARY KEY (
                                              `no`
        );

ALTER TABLE tb_event
    MODIFY no INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `tb_member`
    ADD CONSTRAINT `PK_TB_MEMBER` PRIMARY KEY (
                                               `no`
        );

ALTER TABLE tb_member
    MODIFY no INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `tb_res`
    ADD CONSTRAINT `PK_TB_RES` PRIMARY KEY (
                                            `no`
        );

ALTER TABLE tb_res
    MODIFY no INT NOT NULL AUTO_INCREMENT;

-- 예약상태 기본값 : '완료'
alter table tb_res
    modify status varchar(20) null default '완료';

-- 외래키세팅
ALTER TABLE `tb_res`
    ADD CONSTRAINT `FK_tb_event_TO_tb_res_1` FOREIGN KEY (
                                                          `bno`
        )
        REFERENCES `tb_event` (
                               `no`
            );

ALTER TABLE `tb_res`
    ADD CONSTRAINT `FK_tb_member_TO_tb_res_1` FOREIGN KEY (
                                                           `pno`
        )
        REFERENCES `tb_member` (
                                `no`
            );

alter table tb_res add unique key (bno, pno);


desc tb_event;
desc tb_res;
desc tb_member;

-- sample insert
insert into tb_event(title, writer, content, event_start_date, event_end_date, location)
values ('기안84 개인전', '이벤트관리자', '기안84개인전 입니다.', '2020-2-2', '2020-2-2', '강남');
insert into tb_event(title, writer, content, event_start_date, event_end_date, location)
values ('뮤지컬<레미제라블>', '이벤트관리자', '뮤지컬<레미제라블>입니다.', '2021-12-22', '2022-1-13', '블루스퀘어 신한카드홀');
insert into tb_event(title, writer, content, event_start_date, event_end_date, location)
values ('뮤지컬<드라큘라>', '이벤트관리자', '뮤지컬<드라큘라>입니다.', '2023-2-2', '2024-2-2', '세종문화회관 대극장');
insert into tb_event(title, writer, content, event_start_date, event_end_date, location)
values ('카시오 스가 개인전', '이벤트관리자', '카시오 스가 개인전 입니다.', '2023-12-14', '2024-2-18', '부산 해운대구');

insert into tb_member(name, email)
values ('test7', 'test7@naver.com'),
        ('test1', 'test1@naver.com'),
        ('test2', 'test2@naver.com'),
        ('test3', 'test3@naver.com'),
        ('test4', 'test4@naver.com'),
        ('test5', 'test5@naver.com'),
       ('test6', 'test6@google.com');

insert into tb_res(bno, pno)
values ((select no from tb_event limit 1),
        (select no from tb_member limit 1));



-- 삭제
 #delete from tb_member;
 #delete from tb_res;
 #delete from tb_event;


select
              te.no,
              te.title,
              te.writer,
              te.content,
              te.event_start_date,
              te.event_end_date,
              te.created_date,
              te.location,
              count(tr.status) cnt
          from  tb_event te
              left outer join tb_res tr
                  on te.no = tr.bno
          group by te.no;

select
    tr.no,
    tm.name,
    te.title,
    te.location,
    te.event_start_date,
    te.event_end_date,
    tr.status,
    tr.created_date

from tb_res tr
         left outer join tb_event te
                         on tr.bno = te.no
         left outer join tb_member tm
                         on tr.pno = tm.no
where tm.email = 'hong@naver.com'


select *
from tb_res tr
left outer join tb_event te
    on tr.bno = te.no

;
