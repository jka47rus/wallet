
create table if not exists wallets (id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 amount DECIMAL);

insert into wallets (amount)
values
(0),
(5000.00),
(7200.30),
(89000.10);