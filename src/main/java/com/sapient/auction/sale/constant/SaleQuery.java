package com.sapient.auction.sale.constant;

public final class SaleQuery {
	public static final String SALE_ID = "from Sale where id = :id";
	public static final String ALL_SALE = "from Sale";
	public static final String LATEST_BID = "from Bid b where b.sale.id = :saleId";
}
