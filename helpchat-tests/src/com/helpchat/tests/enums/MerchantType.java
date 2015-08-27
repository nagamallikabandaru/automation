/**
 * 
 */
package com.helpchat.tests.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:sumved.shami@akosha.in">Sumved Shami</a>
 *
 */
public enum MerchantType {

	OWNER(8);

	private Integer type;

	private static final Map<Integer, MerchantType> intToStatusMap = new HashMap<Integer, MerchantType>();
	static {
		for (MerchantType merchantType : MerchantType.values()) {
			intToStatusMap.put(merchantType.type, merchantType);
		}
	}

	public static MerchantType fromType(int type) {

		MerchantType merchantType = intToStatusMap.get(Integer.valueOf(type));

		return merchantType;
	}

	/**
   * 
   */
	private MerchantType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
}
