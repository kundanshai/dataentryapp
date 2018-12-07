/************************************************************************************************************
 * @(#) ScreenAccessValidator.java   24 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.validator;

import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;

import com.tcs.djb.util.AppLog;

/**
 * <p>
 * Validate Screen access corresponding to a role or user Id.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 24-04-2013
 * @history 14-06-2013 Matiur Rahman added line
 *          responseStringSB.append("DeleteCookie(\"IsRefresh\");"); to
 *          {@link #ajaxResponse()}.
 * 
 */
@SuppressWarnings("deprecation")
public class ScreenAccessValidator {
	/**
	 * <p>
	 * Set the Ajax response for Invalid Access.
	 * </p>
	 * 
	 * @param messageString
	 * @return
	 */
	public static StringBufferInputStream ajaxResponse(String messageString) {
		StringBufferInputStream stringBufferInputStream = null;
		try {
			StringBuffer responseStringSB = new StringBuffer();
			responseStringSB.append("<font color='red'>");
			responseStringSB.append("<b>");
			responseStringSB.append(messageString);
			responseStringSB.append("</b>");
			responseStringSB.append("</font>");
			responseStringSB
					.append("<script language=\"text/javascript\" id=\"ajaxScript\">");
			responseStringSB.append("if(alertCount==0){");
			responseStringSB.append("alertCount++;");
			responseStringSB.append("alert('" + messageString + "');");
			responseStringSB
					.append("if(document.getElementById(\"displaybox\")){");
			responseStringSB.append("hideScreen();}");
			responseStringSB.append("DeleteCookie(\"IsRefresh\");");
			responseStringSB
					.append("document.location.href=\"logout.action?type="
							+ messageString + "\";");
			responseStringSB.append("}");
			responseStringSB.append("</script>");
			stringBufferInputStream = new StringBufferInputStream(
					responseStringSB.toString());
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return stringBufferInputStream;
	}

	/**
	 * <p>
	 * Validate Screen access to a role or user.
	 * </p>
	 * 
	 * @param session
	 * @param screenId
	 * @return true or false
	 */
	@SuppressWarnings("unchecked")
	public static boolean validate(Map<String, Object> session, String screenId) {
		boolean isValid = false;
		try {
			String userRoleId = (String) session.get("userRoleId");
			String userId = (String) session.get("userId");
			Map<String, Object> roleScreenMap = (HashMap<String, Object>) session
					.get("roleScreenMap");
			Map<String, Object> userScreenMap = (HashMap<String, Object>) session
					.get("userScreenMap");
			if (null != userRoleId && null != roleScreenMap
					&& null != userScreenMap) {
				if (userRoleId.equals(roleScreenMap.get(screenId))
						|| userId.equals(userScreenMap.get(screenId))) {
					isValid = true;
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		return isValid;
	}
}
