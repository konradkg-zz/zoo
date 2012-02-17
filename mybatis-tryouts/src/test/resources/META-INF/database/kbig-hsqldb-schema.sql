
   create table identification_numbers (
      idn_id               int                  not null,
      idn_le_id            int                  not null,
      idn_src_id           int                  not null,
      idn_case_ref_no      varchar(100)         not null,
      idn_dv_type          tinyint              not null,
      idn_number           varchar(100)         not null,
      idn_ic_id            int                  not null,
      idn_registry         varchar(255)         null,
      idn_source_date_update datetime             null,
      idn_date_inserted    datetime             not null /*default getdate()*/,
      idn_date_modified    datetime             not null /*default getdate()*/,
	  constraint idn_pk primary key (idn_id)
   );

   create index idb_uk on identification_numbers (
   idn_number ASC,
   idn_dv_type ASC,
   idn_ic_id ASC,
   idn_registry ASC,
   idn_le_id ASC
   );

   create table payment_experiences (
      pex_id               int                  not null,
      pex_kbig_id          bigint               not null,
      pex_src_id           int                  not null,
      pex_case_ref_no      varchar(100)         not null,
      pex_dv_type          tinyint              not null,
      pex_can_publish_creditor_data bit         default 0 not null,
      pex_dv_entitlement   tinyint              default 0 not null,
      pex_entitlement_text varchar(255)         null,
      pex_entitlement_document_ref_no varchar(100)         null,
      pex_icur_id          int                  null,
      pex_amount_total     decimal(19,2)        null,
      pex_amount_open      decimal(19,2)        null,
      pex_is_questioned_by_debtor bit           default 0 not null ,
      pex_amount_questioned_by_debtor decimal(19,2)        null,
      pex_date_due         date        null,
      pex_date_paid        date        null,
      pex_date_of_debtor_notification date        null,
      pex_date_blocked_to  date        null,
      pex_proceeding_is_in_court bit            default 0 not null,
      pex_proceedings_info varchar(/*max*/ 1024)         null,
      pex_enforceable_title_ref_no varchar(100)         null,
      pex_enforceable_title_court_info varchar(255)         null,
      pex_enforceable_title_date date        null,
      pex_mrs_id           smallint             not null,
      pex_ba_id            int                  not null,
      pex_source_date_update datetime             null,
      pex_date_inserted    datetime             not null /*default getdate()*/,
      pex_date_modified    datetime             not null /*default getdate()*/,
	  constraint pex_pk primary key (pex_id)
   );

   create table pex_legal_entities (
      le_id                int                  not null,
      le_pex_id            int                  not null,
      le_src_id            int                  not null,
      le_case_ref_no       varchar(100)         not null,
      le_dv_entity_usage_in_pex tinyint              not null,
      le_rol_id            smallint             default 0 not null,
      le_dv_legal_type     tinyint              not null,
      le_pesel             varchar(11)          null,
      le_nip               varchar(10)          null,
      le_regon             varchar(15)          null,
      le_dow_osob          varchar(9)           null,
      le_passport_pl       varchar(9)           null,
      le_passport_foreign  varchar(30)          null,
      le_ic_id_foreign_identification int                  null,
      le_foreign_registry  varchar(255)         null,
      le_foreign_identification_number varchar(50)          null,
      le_representatives   varchar(255)         null,
      le_ind_id            int                  null,
      le_industry_other    varchar(255)         null,
      le_first_name        varchar(255)         null,
      le_name              varchar(255)         not null,
      le_street            varchar(200)         null,
      le_house_number      varchar(50)          null,
      le_flat_number       varchar(20)          null,
      le_zip               varchar(12)          null,
      le_city              varchar(100)         null,
      le_ic_id             int                  null,
      le_contact_street    varchar(200)         null,
      le_contact_house_number varchar(50)          null,
      le_contact_flat_number varchar(20)          null,
      le_contact_zip       varchar(12)          null,
      le_contact_city      varchar(100)         null,
      le_ic_id_contact     int                  null,
      le_ba_id             int                  not null,
      le_source_date_update datetime             null,
      le_date_inserted     datetime             not null /*default getdate()*/,
      le_date_modified     datetime             not null /*default getdate()*/,
	  constraint le_pk primary key (le_id)
   );

   create table roles (
      rol_id               smallint             identity,
      rol_name             varchar(100)         not null,
      rol_description      varchar(255)         null,
      rol_is_active        bit                  default 0 not null,
      rol_date_inserted    datetime             not null /*default getdate()*/,
      rol_date_modified    datetime             not null /*default getdate()*/
   );

   create index rol_uk on roles (
   rol_name ASC
   );

   alter table identification_numbers
      add constraint idn_fk_le foreign key (idn_le_id)
         references pex_legal_entities (le_id);

	alter table pex_legal_entities
      add constraint le_fk_pex foreign key (le_pex_id)
         references payment_experiences (pex_id);
            
   alter table pex_legal_entities
      add constraint le_fk_rol foreign key (le_rol_id)
         references roles (rol_id);
