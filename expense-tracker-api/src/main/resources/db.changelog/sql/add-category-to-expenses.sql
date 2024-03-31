alter table expenses add column category_id
        uuid references expense_categories (id)