# LibManager
A library manage system
# DEMO
### 搜索
![image14](https://github.com/user-attachments/assets/fd22156d-463f-4e20-b5f5-9d154ce6ab65)
### 借阅/归还
![image15](https://github.com/user-attachments/assets/6da49086-0d18-4a31-bc0a-5850965fd1cf)
![image16](https://github.com/user-attachments/assets/80f9f326-f6b5-492b-9940-7469e810b748)
### 后台
![image17](https://github.com/user-attachments/assets/8806916c-8c84-4f51-a7f0-2fef8cfc44ca)
### 书籍管理
![image18](https://github.com/user-attachments/assets/c32b56dc-bf99-49fa-81b6-a80a0197f1a8)
![image19](https://github.com/user-attachments/assets/eb93a26d-3b5d-4ed3-aae5-56b51950d972)
![image20](https://github.com/user-attachments/assets/81a34c04-a09e-482c-854c-c1d012604b61)
![image21](https://github.com/user-attachments/assets/2c30d308-6cf5-44ad-b4f0-be38c5a2b55e)
![image22](https://github.com/user-attachments/assets/63b66e99-81de-40b4-93de-1f3490b6d15c)
![image23](https://github.com/user-attachments/assets/a72db7f1-448a-4b9c-812e-b26f10383f5e)
# 数据库
gz-cdb-5vzxa0ap.sql.tencentcdb.com:63812

# 环境
需自行安装mysql的java驱动程序：http://dev.mysql.com/downloads/connector/j/

请参阅该教程：https://www.runoob.com/java/java-mysql-connect.html

项目结构按照SpringMVC的结构进行设计，但并非为了使用SpringMVC，而是为了方便后期的扩展。
# 项目结构

## 角色
- 管理员
- 普通用户

## 功能

### **书籍相关**

添加书籍数据（管理员）

默认提供三种书籍信息模板
- 书籍
- 报纸
- 期刊

(其他扩展根据时间考虑是否添加)

修改书籍数据（管理员）

删除书籍数据（管理员）

查询书籍（用户，管理员）

按照书籍库中的书籍，添加书籍状态（管理员）

修改书籍状态（管理员）

查询书籍状态（用户，管理员）

借阅书籍（用户，管理员）

归还书籍（用户，管理员）

### **用户管理**

用户注册（首次）

提权用户（管理员）

查询用户（管理员）

编辑用户（管理员）

删除用户（管理员）

### **交互界面**
**主菜单**

[书籍]

[用户注册]

[用户登录]

[用户注销]\(登录前不可见\)

**二级菜单**!

**[书籍]**
- [查寻书籍]
- [归还书籍]

**\(管理员\)**
- [添加书籍]
- [修改书籍]
- [删除书籍]

[用户注册]
- 注册表格，注册完毕后退回一级界面

[用户登录]
- 登录表格，登录完毕后退回一级界面，将用户登录和用户注册消失

[用户注销]
- 选择后进行一次提示，确认后注销用户登录状态，恢复[用户注册]和[用户登录]的可见度，删除[用户注销]的可见度

## UML Class
https://www.processon.com/v/644fd4c67cacb139ebbb9ddd

[Lib System (1) (1)](https://user-images.githubusercontent.com/49596040/236640150-d6cd28b5-b387-414a-b2d1-218c8d07b86a.svg)
