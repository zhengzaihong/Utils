package com.dz.utlis;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2019/6/14
 * create_time: 16:27
 * describe: 模拟消息队列实现一个固定大小的数据队列,超出自动去除第一位元素
 **/

public class MessageQueue<E> implements Queue<E> {

    /**
     * 队列长度，实例化类的时候指定
     */
    private int queueSize;

    private Queue<E> queue = new LinkedList<>();

    public MessageQueue(int queueSize) {
        this.queueSize = queueSize;
    }
    /**
     * 入队
     */
    @Override
    public boolean offer(E e) {
        if (queue.size() >= queueSize) {
            //如果超出长度,入队时,先出队
            queue.poll();
        }
        return queue.offer(e);
    }

    /**
     * 出队
     */
    @Override
    public E poll() {
        return queue.poll();
    }

    /**
     * 获取队列
     */
    public Queue<E> getQueue() {
        return queue;
    }

    /**
     * 获取限制大小
     */
    public int getQueueSize() {
        return queueSize;
    }

    @Override
    public boolean add(E e) {
        return queue.add(e);
    }

    @Override
    public E element() {
        return queue.element();
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public boolean isEmpty() {
        return queue.size() == 0 ? true : false;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public E remove() {
        return queue.remove();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return queue.addAll(c);
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }
}