CREATE TABLE IF NOT EXISTS public.payment_method (
    id bigserial NOT NULL,
    description TEXT,
    payment_type TEXT,
    CONSTRAINT payment_method_pkey PRIMARY KEY (id)
);

COMMENT ON COLUMN public.payment_method.payment_type IS 'DEBIT/CREDIT/PIX';

CREATE TABLE IF NOT EXISTS public.customer (
    id bigserial NOT NULL,
    name TEXT NOT NULL,
    cpf TEXT NOT NULL,
    phone TEXT,
    email TEXT,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_customer_name ON public.customer USING btree (name);

CREATE TABLE IF NOT EXISTS public.category (
    id bigserial NOT NULL,
    description TEXT NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.product (
    id bigserial NOT NULL,
    description TEXT NOT NULL,
    unit_price NUMERIC(15,2),
    category_id bigint,
    image TEXT NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);