package com.hodo.study.concurrency.stock.service;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReentrantLockStockService {
	private final ReentrantLock reentrantLock = new ReentrantLock(true);	// true : 공정모드, false : 비공정 모드
	private final StockService StockService;

	// @Transactions 선언 시, 하위 트랙잭션이 상위로 묶이기 때문에 동시성 제어 불가능
	public void decrease(final Long id, final int quantity) {
		try {
			reentrantLock.lockInterruptibly();	// Lock 획득 시도

			StockService.decrease(id, quantity); // 재고 감소
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			reentrantLock.unlock(); // Lock 반환
		}

	}
}
