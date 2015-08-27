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
public enum MerchantStatus {

  VERIFIED(2), UNVERIFIED(3), DISABLED(4);

  private Integer status;

  private static final Map<Integer, MerchantStatus> intToStatusMap =
      new HashMap<Integer, MerchantStatus>();
  static {
    for (MerchantStatus type : MerchantStatus.values()) {
      intToStatusMap.put(type.status, type);
    }
  }

  public static MerchantStatus fromStatus(int i) {

    MerchantStatus type = intToStatusMap.get(Integer.valueOf(i));

    return type;
  }

  /**
   * 
   */
  private MerchantStatus(Integer status) {
    this.status = status;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }
}
