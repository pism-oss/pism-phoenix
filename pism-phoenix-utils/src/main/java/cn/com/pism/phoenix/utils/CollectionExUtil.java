package cn.com.pism.phoenix.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 集合扩展工具类
 *
 * @author perccyking
 * @since 24-06-03 17:49
 */
public class CollectionExUtil {

    private CollectionExUtil() {
    }

    /**
     * <p>
     * 集合不为空的时候执行回调（无参回调）
     * </p>
     *
     * @param collection : 集合
     * @param isNotEmpty : 不为空回调
     */
    public static <T> void isNotEmpty(Collection<T> collection, Runnable isNotEmpty) {
        isNotEmpty(collection, co -> isNotEmpty.run());
    }

    /**
     * <p>
     * 当集合不为空时执行回调
     * </p>
     *
     * @param collection : 集合
     * @param isNotEmpty : 回调
     */
    public static <T> void isNotEmpty(Collection<T> collection, Consumer<Collection<T>> isNotEmpty) {
        isNotEmpty(collection, isNotEmpty, null);
    }

    public static <T> void isNotEmpty(Collection<T> collection, Consumer<Collection<T>> isNotEmpty, Runnable isEmpty) {
        if (!CollectionUtils.isEmpty(collection)) {
            isNotEmpty.accept(collection);
        } else if (isEmpty != null) {
            isEmpty.run();
        }
    }

    public static <T> void isNotEmpty(Collection<T> collection, Runnable isNotEmpty, Runnable isEmpty) {
        isNotEmpty(collection, co -> isNotEmpty.run(), isEmpty);
    }

    /**
     * <p>
     * 当集合不为空时遍历
     * </p>
     *
     * @param collection    : 集合
     * @param isNotEmptyFor : 遍历元素回调
     */
    public static <T> void isNotEmptyFor(Collection<T> collection, Consumer<T> isNotEmptyFor) {
        isNotEmptyFor(collection, isNotEmptyFor, null);
    }

    public static <T> void isNotEmptyForElse(Collection<T> collection, Consumer<T> isNotEmpty, Runnable isEmpty) {
        if (CollectionUtils.isEmpty(collection)) {
            if (isEmpty != null) {
                isEmpty.run();
            }
        } else {
            isNotEmptyFor(collection, isNotEmpty, null);
        }
    }

    /**
     * <p>
     * 当集合不为空时遍历
     * </p>
     *
     * @param collection    : 集合
     * @param isNotEmptyFor : 遍历元素回调
     * @param after         : 执行后回调
     */
    public static <T> void isNotEmptyFor(Collection<T> collection, Consumer<T> isNotEmptyFor, Consumer<Collection<T>> after) {
        isNotEmpty(collection, coll -> {
            coll.forEach(isNotEmptyFor);
            if (after != null) {
                after.accept(collection);
            }
        });
    }


    /**
     * <p>
     * 添加元素到list中
     * </p>
     *
     * @param list    : 列表
     * @param item    : 元素
     * @param isEmpty : list为空的时候回调
     */
    public static <T> void addToList(List<T> list, T item, Consumer<List<T>> isEmpty) {
        boolean listIsEmpty = false;
        if (list == null) {
            list = new ArrayList<>();
            listIsEmpty = true;
        }
        list.add(item);
        if (listIsEmpty && isEmpty != null) {
            isEmpty.accept(list);
        }
    }

    /**
     * <p>
     * 将元素添加到对象的list中
     * </p>
     *
     * @param obj    : 对象
     * @param getter : 获取对象中的list的getter
     * @param setter : 设置对象list的setter
     * @param item   : 待添加的元素
     */
    public static <T, R> void addToList(T obj, Function<T, List<R>> getter, Consumer<List<R>> setter, R item) {
        List<R> list = getter.apply(obj);
        if (list != null) {
            list.add(item);
        } else {
            list = new ArrayList<>();
            list.add(item);
            setter.accept(list);
        }
    }

    /**
     * <p>
     * 取topN
     * </p>
     *
     * @param collection : 集合
     * @param n          : 个数
     * @param comparator : 排序比较器
     * @return {@link List<T>}
     */
    public static <T> List<T> getTop(Collection<T> collection, Integer n, Comparator<T> comparator) {
        return sortPage(collection, comparator, 1, n);
    }

    /**
     * <p>
     * 对集合进行排序
     * </p>
     * by PerccyKing
     *
     * @param collection : 待排序集合
     * @param comparator : 排序比较器
     * @return {@link List<T>} 已排序的集合
     */
    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream().sorted(comparator).toList();
    }

    /**
     * <p>
     * 分页排序
     * </p>
     * by PerccyKing
     *
     * @param collection : 集合
     * @param comparator : 排序比较器
     * @param pageNum    : 第几页
     * @param pageSize   : 分页大小
     * @return {@link List<T>} 排序后的页
     */
    public static <T> List<T> sortPage(Collection<T> collection, Comparator<T> comparator, int pageNum, int pageSize) {
        List<T> list = sort(collection, comparator);
        return page(list, pageNum, pageSize);
    }


    /**
     * <p>
     * 对集合进行分页
     * </p>
     * by PerccyKing
     *
     * @param collection : 集合
     * @param pageNum    : 第几页
     * @param pageSize   : 分页大小
     * @return {@link List<T>}
     */
    public static <T> List<T> page(Collection<T> collection, int pageNum, int pageSize) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        List<List<T>> partition = Lists.partition(new ArrayList<>(collection), pageSize);
        return partition.get(pageNum - 1);
    }
}
