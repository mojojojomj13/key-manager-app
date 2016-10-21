package com.crm.keymanager.constants;

/**
 * This class contains all the Queries needed to be run against the DB
 * 
 * @author Prithvish Mukherjee, Akhilesh Mittal, Manmeet Singh
 */
public class QueryConstants {

	public static final String GET_ROLES_QRY = " SELECT * FROM USER_ROLES WHERE LOWER(USERNAME) = LOWER(?) ";

	public static final String GET_USER_QRY = " SELECT * FROM USERS U WHERE LOWER(U.USERNAME) = LOWER(?) AND U.PASSWORD = ? ";

}
