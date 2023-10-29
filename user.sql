alter session set "_ORACLE_SCRIPT"= true;

CREATE USER kjvargas IDENTIFIED BY 12345;

GRANT DBA TO kjvargas;

GRANT CONNECT, RESOURCE TO kjvargas;