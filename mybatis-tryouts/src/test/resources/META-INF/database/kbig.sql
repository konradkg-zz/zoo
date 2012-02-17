--
-- Deltavista (C) 2/17/2012 10:41:00 AM
--
-- $Header: $
--
-- Description:
-- kbig definition.
--


use kbig
go

if object_id(N'identification_numbers', N'U') is null begin
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
      idn_date_inserted    datetime             not null default getdate(),
      idn_date_modified    datetime             not null default getdate()
   )
   
end;
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', null, null)) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Identification numbers',
   'schema', @CurrentUser, 'table', 'identification_numbers'
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Identification number id.',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_le_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_le_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Legal entity id. Refers to kbig.dbo.legal_entities.',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_le_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_src_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_src_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Source Id. Refers to prd.dbo.sources.',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_src_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_case_ref_no')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_case_ref_no';
end;
execute sp_addextendedproperty 'MS_Description', 
   'PEX case reference number.',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_case_ref_no';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_dv_type')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_dv_type';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Identification number type. Refers to @dictionary=tobedefined',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_dv_type';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_number')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_number';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Identification number',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_number';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_ic_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_ic_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Country Id of identification number.',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_ic_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_registry')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_registry';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Registry that issues identification number',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_registry';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_source_date_update')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_source_date_update';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date when record was delivered/confirmed by the source.',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_source_date_update';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_date_inserted')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_date_inserted';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date of record insertion.',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_date_inserted';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_date_modified')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_date_modified';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date of last record modification.',
   'schema', @CurrentUser, 'table', 'identification_numbers', 'column', 'idn_date_modified';
go

if not exists (select 1
             from sys.indexes
            where name = 'idn_pk'
              and object_id = object_id(N'identification_numbers', N'U')) begin
   create unique clustered index idn_pk on identification_numbers (
   idn_id ASC
   )
   
end
go

if not exists (select 1
             from sys.indexes
            where name = 'idb_uk'
              and object_id = object_id(N'identification_numbers', N'U')) begin
   create index idb_uk on identification_numbers (
   idn_number ASC,
   idn_dv_type ASC,
   idn_ic_id ASC,
   idn_registry ASC,
   idn_le_id ASC
   )
   
end
go

if object_id(N'payment_experiences', N'U') is null begin
   create table payment_experiences (
      pex_id               int                  not null,
      pex_kbig_id          bigint               not null,
      pex_src_id           int                  not null,
      pex_case_ref_no      varchar(100)         not null,
      pex_dv_type          tinyint              not null,
      pex_can_publish_creditor_data bit                  not null default 0,
      pex_dv_entitlement   tinyint              not null default 0,
      pex_entitlement_text varchar(255)         null,
      pex_entitlement_document_ref_no varchar(100)         null,
      pex_icur_id          int                  null,
      pex_amount_total     decimal(19,2)        null,
      pex_amount_open      decimal(19,2)        null,
      pex_is_questioned_by_debtor bit                  not null default 0,
      pex_amount_questioned_by_debtor decimal(19,2)        null,
      pex_date_due         smalldatetime        null,
      pex_date_paid        smalldatetime        null,
      pex_date_of_debtor_notification smalldatetime        null,
      pex_date_blocked_to  smalldatetime        null,
      pex_proceeding_is_in_court bit                  not null default 0,
      pex_proceedings_info varchar(max)         null,
      pex_enforceable_title_ref_no varchar(100)         null,
      pex_enforceable_title_court_info varchar(255)         null,
      pex_enforceable_title_date smalldatetime        null,
      pex_mrs_id           smallint             not null,
      pex_ba_id            int                  not null,
      pex_source_date_update datetime             null,
      pex_date_inserted    datetime             not null default getdate(),
      pex_date_modified    datetime             not null default getdate()
   )
   
end;
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', null, null)) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Payment Experiences.',
   'schema', @CurrentUser, 'table', 'payment_experiences'
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Primary key.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_kbig_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_kbig_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'KBIG Id. Generated internally.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_kbig_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_src_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_src_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Source Id. Refers to prd.dbo.sources',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_src_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_case_ref_no')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_case_ref_no';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Case reference number',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_case_ref_no';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_dv_type')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_dv_type';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Payment experience type (0-Neutral, 1-Negative, 2-Positive)',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_dv_type';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_can_publish_creditor_data')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_can_publish_creditor_data';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Indicates whether we''re allowed to publish creditor''s data on the reports.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_can_publish_creditor_data';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_dv_entitlement')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_dv_entitlement';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Entitlement for reporting payment experience. It can be for example "Sell in instalments", "Leasing agreement", "Standard sell agreement" etc.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_dv_entitlement';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_entitlement_text')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_entitlement_text';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Entitlement text, if not available as pre-defined value in pex_dv_entitlement field.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_entitlement_text';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_entitlement_document_ref_no')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_entitlement_document_ref_no';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Reference number of entitlement document.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_entitlement_document_ref_no';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_icur_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_icur_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Currency id. Refers to ref.dbo.iso_currencies',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_icur_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_total')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_total';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Total amount of debt or positive payment experience.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_total';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_open')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_open';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Amount to be paid.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_open';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_is_questioned_by_debtor')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_is_questioned_by_debtor';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Indicates whether this debt was questioned by the debtor (1) or not (0)',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_is_questioned_by_debtor';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_questioned_by_debtor')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_questioned_by_debtor';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Amount questioned by the Debtor',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_amount_questioned_by_debtor';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_due')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_due';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date until which payment was due.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_due';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_paid')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_paid';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date when case was paid.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_paid';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_of_debtor_notification')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_of_debtor_notification';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date when debtor was notified about the fact that case will be sent to BIG.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_of_debtor_notification';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_blocked_to')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_blocked_to';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date until which case if blocked i.e. not exposed on reports.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_blocked_to';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_proceeding_is_in_court')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_proceeding_is_in_court';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Indicates whether case is now sent to court',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_proceeding_is_in_court';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_proceedings_info')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_proceedings_info';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Information about proceedings related to the case.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_proceedings_info';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_ref_no')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_ref_no';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Reference number of enforceable title document.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_ref_no';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_court_info')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_court_info';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Usually, court name and address responsible for enforceable title.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_court_info';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_date')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_date';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date of enforceable title.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_enforceable_title_date';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_mrs_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_mrs_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Master risk status id. Refers to kbig.dbo.master_risk_statuses',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_mrs_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_ba_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_ba_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Batch Id of last update. Refers to prd.dbo.data_batches.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_ba_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_source_date_update')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_source_date_update';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date when record was delivered/confirmed by the source.',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_source_date_update';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_inserted')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_inserted';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date of record insertion',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_inserted';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_modified')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_modified';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date of last record modification',
   'schema', @CurrentUser, 'table', 'payment_experiences', 'column', 'pex_date_modified';
go

if not exists (select 1
             from sys.indexes
            where name = 'pex_pk'
              and object_id = object_id(N'payment_experiences', N'U')) begin
   create unique clustered index pex_pk on payment_experiences (
   pex_id ASC
   )
   
end
go

if object_id(N'pex_legal_entities', N'U') is null begin
   create table pex_legal_entities (
      le_id                int                  not null,
      le_pex_id            int                  not null,
      le_src_id            int                  not null,
      le_case_ref_no       varchar(100)         not null,
      le_dv_entity_usage_in_pex tinyint              not null,
      le_rol_id            smallint             not null default 0,
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
      le_date_inserted     datetime             not null default getdate(),
      le_date_modified     datetime             not null default getdate()
   )
   
end;
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', null, null)) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Legal entities i.e. Private Persons & Companies related to given payment experience record.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities'
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Primary key.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_pex_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_pex_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Payment experience id.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_pex_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_src_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_src_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Source Id. Refers to prd.dbo.sources.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_src_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_case_ref_no')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_case_ref_no';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Case reference number',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_case_ref_no';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dv_entity_usage_in_pex')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dv_entity_usage_in_pex';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Type usage of legal entity in PEX. Allowed values are: 1-Creditor, 2-Debtor, 3-Related',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dv_entity_usage_in_pex';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_rol_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_rol_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Role of the legal entity in PEX. Refers to kbig.dbo.roles.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_rol_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dv_legal_type')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dv_legal_type';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Legal Type: 1-Private Person, 2-Single Person Company, 4-Multiperson Company, 6-Limited Company, 7-Public company',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dv_legal_type';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_pesel')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_pesel';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Pesel identifier (Person identifier).',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_pesel';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_nip')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_nip';
end;
execute sp_addextendedproperty 'MS_Description', 
   'NIP identifier (tax identifier)',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_nip';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_regon')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_regon';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Regon identifier (Company Identifier)',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_regon';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dow_osob')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dow_osob';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Dowod Osobisty identifier (Person identifier)',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_dow_osob';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_passport_pl')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_passport_pl';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Polish Passport identifier',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_passport_pl';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_passport_foreign')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_passport_foreign';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Other passport identifier',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_passport_foreign';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id_foreign_identification')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id_foreign_identification';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Country id of foreign identification document. Refers to ref.dbo.iso_countries',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id_foreign_identification';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_foreign_registry')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_foreign_registry';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Foreign registry or court name of the company',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_foreign_registry';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_foreign_identification_number')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_foreign_identification_number';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Foreign identification in foreign registry (if provided)',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_foreign_identification_number';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_representatives')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_representatives';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Representatives (first and last names) of the legal entity.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_representatives';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ind_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ind_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Company industry Id. Refers to <p color="red">[to be done]</p>',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ind_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_industry_other')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_industry_other';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Company industry - one that cannot be found in standard industries dictionary.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_industry_other';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_first_name')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_first_name';
end;
execute sp_addextendedproperty 'MS_Description', 
   'First name(s)',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_first_name';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_name')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_name';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Name(s)',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_name';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_street')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_street';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Street of main address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_street';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_house_number')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_house_number';
end;
execute sp_addextendedproperty 'MS_Description', 
   'House number of main address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_house_number';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_flat_number')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_flat_number';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Flat number of main address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_flat_number';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_zip')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_zip';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Zip code of main address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_zip';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_city')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_city';
end;
execute sp_addextendedproperty 'MS_Description', 
   'City name of main address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_city';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Country Id of main address. Refers to ref.dbo.iso_countries.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_street')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_street';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Streef of contact address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_street';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_house_number')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_house_number';
end;
execute sp_addextendedproperty 'MS_Description', 
   'House number of contact address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_house_number';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_flat_number')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_flat_number';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Flat number of contact address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_flat_number';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_zip')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_zip';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Zip code of contact address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_zip';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_city')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_city';
end;
execute sp_addextendedproperty 'MS_Description', 
   'City name of contact address.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_contact_city';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id_contact')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id_contact';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Country Id of contact address. Refers to ref.dbo.iso_countries.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ic_id_contact';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ba_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ba_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Batch Id of last update. Refers to prd.dbo.data_batches.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_ba_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_source_date_update')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_source_date_update';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date when record was delivered/confirmed by the source.',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_source_date_update';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_date_inserted')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_date_inserted';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date of record insertion',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_date_inserted';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_date_modified')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_date_modified';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date of last record modification',
   'schema', @CurrentUser, 'table', 'pex_legal_entities', 'column', 'le_date_modified';
go

if not exists (select 1
             from sys.indexes
            where name = 'le_pk'
              and object_id = object_id(N'pex_legal_entities', N'U')) begin
   create unique clustered index le_pk on pex_legal_entities (
   le_id ASC
   )
   
end
go

if object_id(N'roles', N'U') is null begin
   create table roles (
      rol_id               smallint             identity,
      rol_name             varchar(100)         not null,
      rol_description      varchar(255)         null,
      rol_is_active        bit                  not null default 0,
      rol_date_inserted    datetime             not null default getdate(),
      rol_date_modified    datetime             not null default getdate()
   )
   
end;
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'roles', null, null)) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'roles';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Roles that given legal entity play within another legal entity.',
   'schema', @CurrentUser, 'table', 'roles'
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_id')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_id';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Primary key (Identity)',
   'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_id';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_name')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_name';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Name of the role',
   'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_name';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_description')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_description';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Description',
   'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_description';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_is_active')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_is_active';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Indicates whether role is active i.e. can be used by the system.',
   'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_is_active';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_date_inserted')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_date_inserted';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date of record insertion',
   'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_date_inserted';
go

declare @CurrentUser sysname
select @CurrentUser = user_name();
if exists (select 1
             from fn_listextendedproperty ('MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_date_modified')) begin
   execute sp_dropextendedproperty 'MS_Description', 'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_date_modified';
end;
execute sp_addextendedproperty 'MS_Description', 
   'Date odf last record modification.',
   'schema', @CurrentUser, 'table', 'roles', 'column', 'rol_date_modified';
go

if not exists (select 1
             from sys.indexes
            where name = 'rol_pk'
              and object_id = object_id(N'roles', N'U')) begin
   create unique clustered index rol_pk on roles (
   rol_id ASC
   )
   
end
go

if not exists (select 1
             from sys.indexes
            where name = 'rol_uk'
              and object_id = object_id(N'roles', N'U')) begin
   create index rol_uk on roles (
   rol_name ASC
   )
   
end
go

if object_id(N'.idn_fk_le', N'F') is null begin
   alter table identification_numbers
      add constraint idn_fk_le foreign key (idn_le_id)
         references pex_legal_entities (le_id)
            
            
end;
go

if object_id(N'.le_fk_pex', N'F') is null begin
   alter table pex_legal_entities
      add constraint le_fk_pex foreign key (le_pex_id)
         references payment_experiences (pex_id)
            
            
end;
go

if object_id(N'.le_fk_rol', N'F') is null begin
   alter table pex_legal_entities
      add constraint le_fk_rol foreign key (le_rol_id)
         references roles (rol_id)
            
            
end;
go



--
-- Done.
--
