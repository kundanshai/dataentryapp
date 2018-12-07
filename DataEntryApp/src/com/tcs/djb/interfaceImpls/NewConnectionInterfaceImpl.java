/************************************************************************************************************
 * @(#) NewConnectionInterfaceImpl.java   27 Nov 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.interfaceImpls;

import java.util.HashMap;
import java.util.Map;

import com.tcs.djb.dao.NewConnectionDAO;
import com.tcs.djb.interfaces.NewConnectionInterface;
import com.tcs.djb.model.NewConnectionDAFDetailsContainer;
import com.tcs.djb.services.NewConnectionService;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * <code>NewConnectionInterfaceImpl</code> is the implementation class of the
 * interface created for new connection,NewConnectionInterface This contains the
 * definition of which class to call for the interface.
 * </p>
 * 
 * @author Bency (Tata Consultancy Services)
 * @history 27-11-2013 Matiur Rahman Commented the part which was creating the V
 *          in CC&B as Per JTrac DJB-2123.
 * 
 */
public class NewConnectionInterfaceImpl implements NewConnectionInterface {

	/**
	 * <p>
	 * This method used to save New Connection DAF details .
	 * </p>
	 * (non-Javadoc)
	 * @see com.tcs.djb.interfaces.NewConnectionInterface#saveNewConnectionDAFDetails(com.tcs.djb.model.NewConnectionDAFDetailsContainer)
	 */
	@Override
	public Map<Object, Object> saveNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer) {

		NewConnectionInterface newConnInterface = new NewConnectionDAO();

		Map<Object, Object> returnMap = (HashMap<Object, Object>) newConnInterface
				.saveNewConnectionDAFDetails(newConnectionDAFDetailsContainer);
		String sequenceNo = (String) returnMap.get("sequenceNo");
		AppLog.info("System Generated Sequence No::" + sequenceNo);
		if (null != sequenceNo && !"".equals(sequenceNo)) {
			newConnectionDAFDetailsContainer.setSequenceNo(sequenceNo);
			/*
			 * Start:Added By Matiur Rahman as per New Process as Per JTrac
			 * DJB-2123 on 27/11/2013
			 */
			returnMap.put("sequenceNo", sequenceNo);
			/*
			 * End:Added By Matiur Rahman as per New Process as Per JTrac
			 * DJB-2123 on 27/11/2013
			 */
		} else {
			returnMap.put("errorMessage", "DataBaseError");
			return returnMap;
		}
		/*
		 * Start:Commented By Matiur Rahman as per New Process as Per JTrac
		 * DJB-2123 on 27/11/2013
		 */
		// Calling CCB Service to Create V
		// newConnInterface = new NewConnectionService();
		// returnMap = (HashMap<Object, Object>) newConnInterface
		// .saveNewConnectionDAFDetails(newConnectionDAFDetailsContainer);
		/*
		 * End:Commented By Matiur Rahman as per New Process as Per JTrac
		 * DJB-2123 on 27/11/2013
		 */
		return returnMap;

	}

	/**
	 * <p>
	 * This method used to process New Connection DAF details .
	 * </p>
	 * 
	 * (non-Javadoc)
	 * @see com.tcs.djb.interfaces.NewConnectionInterface#processNewConnectionDAFDetails(com.tcs.djb.model.NewConnectionDAFDetailsContainer, java.lang.String)
	 */
	@Override
	public Map<Object, Object> processNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer,
			String authCookie) {
		// Calling CCB Service to Create V
		NewConnectionInterface newConnInterface = new NewConnectionService();
		Map<Object, Object> returnMap = (HashMap<Object, Object>) newConnInterface
				.processNewConnectionDAFDetails(
						newConnectionDAFDetailsContainer, authCookie);
		return returnMap;
	}

	/**
	 * <p>
	 * This method used to update New Connection DAF details .
	 * </p>
	 * 
	 * (non-Javadoc)
	 * @see com.tcs.djb.interfaces.NewConnectionInterface#updateNewConnectionDAFDetails(com.tcs.djb.model.NewConnectionDAFDetailsContainer)
	 */
	@Override
	public int updateNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer) {
		System.out
				.println("NewConnectionInterfaceImpl updateNewConnectionDAFDetails");
		return 0;
	}

}
