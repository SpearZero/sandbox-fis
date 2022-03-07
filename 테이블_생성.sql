-- 채널 정보
create table channel (
    channel_id      bigint          not null auto_increment,
    created_at      datetime(6)     not null,
    modified_at     datetime(6)     not null,
    channel_name    varchar(90)     not null,
    email           varchar(255)    not null,
    primary key (channel_id)
) engine=InnoDB;

-- 크리에이터 정보
create table creator (
    creator_id      bigint          not null auto_increment,
    created_at      datetime(6)     not null,
    modified_at     datetime(6)     not null,
    email           varchar(255)    not null,
    name            varchar(54)     not null,
    primary key (creator_id)
) engine=InnoDB;

-- 계약서 정보
create table contract (
    contract_id     bigint          not null auto_increment,
    created_at      datetime(6)     not null,
    modified_at     datetime(6)     not null,
    company_rs      integer         not null,
    creator_rs      integer         not null,
    channel_id      bigint          not null,
    primary key (contract_id)
) engine=InnoDB;

-- 크리에이터별 계약서 정보
create table creator_contract (
  creator_contract_id   bigint        not null auto_increment,
  created_at            datetime(6)   not null,
  modified_at           datetime(6)   not null,
  creator_rs            integer       not null,
  contract_id           bigint        not null,
  creator_id            bigint        not null,
  primary key (creator_contract_id)
) engine=InnoDB;

-- 총 정산 금액
create table amount (
    amount_id       bigint          not null auto_increment,
    created_at      datetime(6)     not null,
    modified_at     datetime(6)     not null,
    amount          decimal(22,7)   not null,
    day             date            not null,
    contract_id     bigint          not null,
    primary key (amount_id)
) engine=InnoDB;

-- 회사 정산 금액
create table company_amount (
    company_amount_id   bigint          not null auto_increment,
    created_at          datetime(6)     not null,
    modified_at         datetime(6)     not null,
    amount              decimal(22,7)   not null,
    day                 date            not null,
    amount_id           bigint          not null,
    contract_id         bigint          not null,
    primary key (company_amount_id)
) engine=InnoDB;

-- 크리에이터 정산 금액
create table creator_amount (
    creator_amount_id   bigint          not null auto_increment,
    created_at          datetime(6)     not null,
    modified_at         datetime(6)     not null,
    amount              decimal(22,7)   not null,
    day                 date            not null,
    amount_id           bigint          not null,
    contract_id         bigint          not null,
    creator_id          bigint          not null,
    primary key (creator_amount_id)
) engine=InnoDB;