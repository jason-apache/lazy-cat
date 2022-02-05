create table jpa.student(
    id int8 not null,
    class_room_id text,
    student_ext_id text not null,
    name text,
    age int4,
    sex text,
    create_date timestamp(6),
    primary key (id)
);
-- id 序列
create sequence jpa.stu_id_seq;

create table jpa.student_ext (
    id text not null,
    hobby text,
    be_good_at text,
    primary key (id)
);

create table jpa.table_chair (
    id text not null,
    color text,
    crafts text,
    weight text,
    student_id int8 not null,
    primary key (id)
);

create table jpa.book (
    id text not null,
    name text,
    press text,
    price text,
    student_id int8 not null,
    book_ext_id text not null,
    primary key (id)
);

create table jpa.book_ext (
    id text not null,
    thickness text,
    length text,
    weight text,
    subject_id text not null,
    primary key (id)
);

create table jpa.subject (
    id text not null,
    name text,
    subject_ext_id text not null,
    primary key (id)
);

create table jpa.subject_ext (
    id text not null,
    weight text
);

create table jpa.class_room (
    id text not null,
    code text,
    grade text,
    floor text,
    class_room_ext_id text not null,
    primary key (id)
);

create table jpa.class_room_ext (
    id text not null,
    student_count int4,
    department_id text,
    primary key (id)
);

create table jpa.department (
    id text not null,
    name text,
    department_ext_id text not null,
    primary key (id)
);

create table jpa.department_ext (
    id text not null,
    primary key (id)
);

create table jpa.black_board (
    id text not null,
    length text,
    specification text,
    manufacturing_company text,
    class_room_id text not null,
    primary key (id)
);