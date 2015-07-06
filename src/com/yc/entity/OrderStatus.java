package com.yc.entity;

public enum OrderStatus {
	 //等待买家付款，        买家已付款，           等待卖家发货，	卖家已发货，	            完成交易，				退款申请   ，                                  关闭交易                           退款中的订单                 退款成功                      退款失败
	 waitPayment,BuyersHavePaid, waitDelivery, transitGoods, completionTransaction, ApplicationForRefund,closeTransaction,refundOrderForm,refundSuccess,refundFailed
}
