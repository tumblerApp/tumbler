package com.yc.entity;

public enum OrderStatus {
	 //等待买家付款，        买家已付款，           等待卖家发货，	卖家已发货，	        等待收货人签单，		            完成交易，				关闭交易                           退款中的订单                 退款成功                      退款失败
	 waitPayment,BuyersHavePaid, waitDelivery, transitGoods, consigneeSigning, completionTransaction, closeTransaction,refundOrderForm,refundSuccess,refundFailed
}
