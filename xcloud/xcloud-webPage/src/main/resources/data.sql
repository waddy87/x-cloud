delete from user_role;
delete from role_resource;
delete from user;
delete from role;
delete from resource;

/*用户表*/
INSERT INTO user (id, create_date, email, locked,org_id,org_name,password,realname,salt,telephone,username,is_delete) VALUES ('1', '2016-3-30 18:29:53', '', 0, '', '', 'd3c59d25033dbf980d29554025c23a75', 'admin', '8d78869f470951332959580424d4bf4f', '1234567890', 'admin',0);

/*角色表*/
INSERT INTO role (id, role_name,role_code,  description, available) VALUES ('1', '运营管理员','operation_manager', '', 0);
INSERT INTO role (id, role_name, role_code,description, available) VALUES ('2', '组织管理员', 'org_manager', '',0);
INSERT INTO role (id, role_name, role_code,description, available) VALUES ('3', '组织用户', 'org_user', '',0);

/*角色表_用户表*/
INSERT INTO user_role (role_id, user_id) VALUES ('1', '1');

/*资源表*/
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (1, '云资源', 'menu', '','cloudresource:*',0, '0/',0,1,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (101, '虚拟化环境', 'menu', '/venv/getConfigInfo','venv:*',1, '1/', 0,101,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (102, '提供者vDC', 'menu', '/proVDC/providerVDCList','provdc:*',1, '1/', 0,102,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (103, '虚拟机网络', 'menu', '/vnet/vnetList','vnet:*',1, '1/',  0,103,'');



INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (2, '云服务', 'menu', '','cloudservices:*',0, '0/',0,2,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (201, '云主机', 'menu', '','cloudhost:*',2, '2/',0,201,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (202, '模版管理', 'menu', '/templet/toVMTempletIndex','templet:*',2, '2/',0,202,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (203, '资源申请', 'menu', '','serviceapproval:*',2, '2/',0,203,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (204, '记账管理', 'menu', '','accountmgmt:*',2, '2/',0,204,'');



INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (3, '云用户', 'menu', '','cloudusers:*',0, '0/',0,3,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (301, '组织管理', 'menu', '/orgs/orgIndex','orgs:*',3, '3/',0,301,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (302, '用户管理', 'menu', '/userMgmt/userIndex','user:*',3, '3/',0,302,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (303, '创建用户', 'button', '','user:create',302, '3/302',0,303,'');

INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (4, '虚拟机', 'menu', '','vm:*',0, '0/', 0,4,'');

INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (5, '利旧虚拟机', 'menu', '','oldvm:*',0, '0/', 0,5,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (501, '监控概览', 'menu', '/oldvm/mandateIndex','oldvmmanaged:*',5, '5/',0,501,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (502, '资源视图', 'menu', '/oldvm/unMandateIndex','oldvmunmanaged:*',5, '5/',0,502,'');

INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (6, '物理机', 'menu', '/pm/pmIndex','pm:*',0, '0/', 0,6,'');


INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (7, '云监控', 'menu', '','cloudmonitor:*',0, '0/',0,7,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (701, '监控概览', 'menu', '/monitor/toMonitorIndex','monitor:*',7, '7/',0,701,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (702, '资源视图', 'menu', '/monitor/toMonitorResource','monitorresource:*',7, '7/',0,702,'');

INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (8, '云报表', 'menu', '','cloudreport:*',0, '0/',0,8,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (9, '云告警', 'menu', '/alert/toAlertIndex','cloudalert:*',0, '0/', 0,9,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (10, '日志管理', 'menu', '','logmemt:*',0, '0/',0,10,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (11, '系统配置', 'menu', '','sysconfigure:*',0, '0/',0,11,'');
INSERT INTO resource (id, name, resource_type, url, permission, parent_id, parent_ids, available, priority, icon_url) VALUES (12, '项目', 'menu', '/action/project/projectIndex','project:*',0, '0/',0,12,'');


/*角色表_资源表*/
/*运营管理员*/
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 1);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 101);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 102);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 103);

INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 2);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 201);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 202);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 203);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 204);

INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 3);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 301);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 302);

INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 4);

INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 5);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 501);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 502);

INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 6);

INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 7);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 701);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 702);

INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 8);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 9);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 10);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 11);
INSERT INTO role_resource (role_id, resource_id) VALUES ('1', 12);

/*组织管理员*/
INSERT INTO role_resource (role_id, resource_id) VALUES ('2', 3);
INSERT INTO role_resource (role_id, resource_id) VALUES ('2', 301);
INSERT INTO role_resource (role_id, resource_id) VALUES ('2', 302);
INSERT INTO role_resource (role_id, resource_id) VALUES ('2', 303);

INSERT INTO role_resource (role_id, resource_id) VALUES ('2', 2);
INSERT INTO role_resource (role_id, resource_id) VALUES ('2', 201);
INSERT INTO role_resource (role_id, resource_id) VALUES ('2', 202);
INSERT INTO role_resource (role_id, resource_id) VALUES ('2', 203);