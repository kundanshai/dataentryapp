/************************************************************************************************************
 * @(#) NewConnectionInterface.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.interfaces;

import java.util.Map;

import com.tcs.djb.model.NewConnectionDAFDetailsContainer;

/**
 * <p>
 * <code>NewConnectionInterface</code> is the new connection interface which
 * contains method saveNewConnectionDAFDetails, processNewConnectionDAFDetails
 * and updateNewConnectionDAFDetails.
 * </p>
 * 
 * @author Bency (Tata Consultancy Services)
 */
public interface NewConnectionInterface {

	/**
	 * <p>
	 * This method used to save New Connection DAF details .
	 * </p>
	 * 
	 * @param newConnectionDAFDetailsContainer
	 * @return
	 */
	public Map<Object, Object> saveNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer);

	/**
	 * <p>
	 * This method used to process New Connection DAF details .
	 * </p>
	 * 
	 * @param newConnectionDAFDetailsContainer
	 * @param authCookie
	 * @return
	 * @author Matiur Rahman(Tata Consultancy Services)
	 */
	public Map<Object, Object> processNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer,
			String authCookie);

	/**
	 * <p>
	 * This method used to update New Connection DAF details .
	 * </p>
	 * 
	 * @param newConnectionDAFDetailsContainer
	 * @return
	 * @author Matiur Rahman(Tata Consultancy Services)
	 */
	public int updateNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer);
}
