package com.cibnvideo.common.sqlprovider;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;

import com.cibnvideo.common.entity.LogDO;

public class LogProvider {

	public String list(LogDO log) {
		Long id = log.getId();
		Long userId = log.getUserId();
		String username = log.getUsername();
		String operation = log.getOperation();
		Integer time = log.getTime();
		String method = log.getMethod();
		String params = log.getParams();
		String ip = log.getIp();
		Date gmtCreate = log.getGmtCreate();
		return new SQL() {
			{
				SELECT("`id`,`user_id`,`username`,`operation`,`time`,`method`,`params`,`ip`,`gmt_create`");
				FROM("sys_log");
				if(id != null) {
					WHERE("id = #{id}");
				}
				if(userId != null) {
					WHERE("user_id = #{userId}");
				}
				if(username != null && !username.isEmpty()) {
					WHERE("username like \"%\"#{username}\"%\"");
				}
				if(operation != null && !operation.isEmpty()) {
					WHERE("operation like \"%\"#{operation}\"%\"");
				}
				if(time != null) {
					WHERE("time = #{time}");
				}
				if(method != null && !method.isEmpty()) {
					WHERE("method = #{method}");
				}
				if(params != null && !params.isEmpty()) {
					WHERE("params = #{params}");
				}
				if(ip != null && !ip.isEmpty()) {
					WHERE("ip = #{ip}");
				}
				if(gmtCreate != null) {
					WHERE("gmt_create = #{gmtCreate}");
				}
				ORDER_BY("id DESC");
			}
		}.toString();
	}
	
	public String count(LogDO log) {
		Long id = log.getId();
		Long userId = log.getUserId();
		String username = log.getUsername();
		String operation = log.getOperation();
		Integer time = log.getTime();
		String method = log.getMethod();
		String params = log.getParams();
		String ip = log.getIp();
		Date gmtCreate = log.getGmtCreate();
		return new SQL() {
			{
				SELECT("COUNT(*)");
				FROM("sys_log");
				if(id != null) {
					WHERE("id = #{id}");
				}
				if(userId != null) {
					WHERE("user_id = #{userId}");
				}
				if(username != null && !username.isEmpty()) {
					WHERE("username like \"%\"#{username}\"%\"");
				}
				if(operation != null && !operation.isEmpty()) {
					WHERE("operation like \"%\"#{operation}\"%\"");
				}
				if(time != null) {
					WHERE("time = #{time}");
				}
				if(method != null && !method.isEmpty()) {
					WHERE("method = #{method}");
				}
				if(params != null && !params.isEmpty()) {
					WHERE("params = #{params}");
				}
				if(ip != null && !ip.isEmpty()) {
					WHERE("ip = #{ip}");
				}
				if(gmtCreate != null) {
					WHERE("gmt_create = #{gmtCreate}");
				}
			}
		}.toString();
	}
	
	public String update(LogDO log) {
		Long id = log.getId();
		Long userId = log.getUserId();
		String username = log.getUsername();
		String operation = log.getOperation();
		Integer time = log.getTime();
		String method = log.getMethod();
		String params = log.getParams();
		String ip = log.getIp();
		Date gmtCreate = log.getGmtCreate();
		return new SQL() {
			{
				UPDATE("sys_log");
				if(userId != null) {
					SET("user_id = #{userId}");
				}
				if(username != null) {
					SET("username = #{username}");
				}
				if(operation != null) {
					SET("operation = #{operation}");
				}
				if(time != null) {
					SET("time = #{time}");
				}
				if(method != null) {
					SET("method = #{method}");
				}
				if(params != null) {
					SET("params = #{params}");
				}
				if(ip != null) {
					SET("ip = #{ip}");
				}
				if(gmtCreate != null) {
					SET("gmt_create = #{gmtCreate}");
				}
			}
		}.toString();
	}
}
