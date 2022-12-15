package org.matmech.connector;

/**
 * Общий интерфейс для всех ботов с обязательным методом <i>start</i>, который запускает всего бота
 */
public interface Connector {
    /**
     * Запускает бота
     */
    void start();
}
