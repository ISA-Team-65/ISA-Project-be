insert into company (company_id, address, company_name, description, rating) values (-1, '123 Main St', 'ABC Corp', 'A technology company', 4.5);
insert into company (company_id, address, company_name, description, rating) values (-2, '456 Oak Ave', 'XYZ Ltd', 'A manufacturing company', 3.8);
insert into company (company_id, address, company_name, description, rating) values (-3, '789 Elm Blvd', '123 Industries', 'An innovative startup', 4.2);
insert into company (company_id, address, company_name, description, rating) values (-4, '555 Pine Lane', 'Global Innovations', 'Multinational corporation', 4.9);
insert into company (company_id, address, company_name, description, rating) values (-5, '101 Cedar Street', 'Tech Solutions Inc.', 'IT consulting firm', 3.5);

insert into administrator_complaint (ad_complaint_id, administrator_id, description) values (-1, 101, 'User reported an issue with account access');
insert into administrator_complaint (ad_complaint_id, administrator_id, description) values (-2, 102, 'Complaint regarding system performance.');
insert into administrator_complaint (ad_complaint_id, administrator_id, description) values (-3, 103, 'User raised concerns about data security.');

insert into company_complaint (comp_complaint_id, company_id, description) values (-1, -2, 'Customer reported a product quality issue.');
insert into company_complaint (comp_complaint_id, company_id, description) values (-2, -3, 'Complaint about delayed shipment.');
insert into company_complaint (comp_complaint_id, company_id, description) values (-3, -5, 'User dissatisfaction with customer service.');