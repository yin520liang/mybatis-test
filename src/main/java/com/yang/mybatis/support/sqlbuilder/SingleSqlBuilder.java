package com.yang.mybatis.support.sqlbuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yang.mybatis.support.sqlbuilder.bean.Expression;
import com.yang.mybatis.support.sqlbuilder.bean.From;
import com.yang.mybatis.support.sqlbuilder.bean.GroupBy;
import com.yang.mybatis.support.sqlbuilder.bean.Order;
import com.yang.mybatis.support.sqlbuilder.bean.OrderByField;
import com.yang.mybatis.support.sqlbuilder.bean.Select;
import com.yang.mybatis.support.sqlbuilder.bean.Where;
import com.yang.mybatis.support.sqlbuilder.bean.impl.MatchMode;
import com.yang.mybatis.support.sqlbuilder.dto.PageDto;
import com.yang.mybatis.support.sqlbuilder.exception.NoIdColumnFoundException;
import com.yang.mybatis.support.sqlbuilder.schema.ColumnDefine;
import com.yang.mybatis.support.sqlbuilder.schema.TableDefine;
import com.yang.mybatis.support.sqlbuilder.util.ClassFieldUtil;
import com.yang.mybatis.support.sqlbuilder.util.ColumnUtil;
import com.yang.mybatis.support.sqlbuilder.util.Expressions;

/**
 * @title SignleSqlBuilder
 * @desc 单表查询SQL构造器
 * @author cxm
 * @date 2015年12月2日
 * @version 1.0
 */
@Slf4j
public class SingleSqlBuilder<VO> {


	private TableDefine tableDefine;


	private Class<VO> poClass;

	private ColumnDefine idColumn;

	private PageDto page;

	@Getter
	@Setter
	private Integer maxSize;

	@Setter
	private int start;

	@Getter
	private SqlBuilderContext context;

	// sql的部分
	private Select select;

	private From from;

	private Where where;


	private List<Order> orders = Lists.newArrayList();

	private OrderByField orderByField;

	private GroupBy groupBy;

	private String having;


	private boolean setDefaultVal;

	private SingleSqlBuilder(Class<VO> poClass) {
		this.poClass = poClass;
		this.tableDefine = ClassFieldUtil.readFieldColumnFromEntityCalss(poClass);
		from = new From(tableDefine.getCatalog(), tableDefine.getName());
		Map<String, String> fieldMapColumn = null;
		Map<String, String> columnMapField = null;
		if (this.tableDefine != null) {
			fieldMapColumn = new HashMap<String, String>();
			columnMapField = new HashMap<String, String>();
			for (ColumnDefine column : tableDefine.getColumnDefines()) {
				if (column.isIdColumn()) {
					if (this.idColumn == null) {
						this.idColumn = column;
					} else {
						throw new UnsupportedOperationException(
								"can not support two id column:" + idColumn + " and:" + column);
					}
				}
				fieldMapColumn.put(column.getFieldName(), column.getColumnName());
				columnMapField.put(column.getColumnName(), column.getFieldName());
			}
		}
		if (this.idColumn == null) {
			log.warn("can not found Id column in poClass:{}", poClass);
			throw new NoIdColumnFoundException(poClass);
		}
		select = new Select();

		where = new Where();
		groupBy = new GroupBy();
		context = new SqlBuilderContext(fieldMapColumn, columnMapField);
	}

	/**
	 * @param poClass2
	 * @param queryProp
	 */
	public SingleSqlBuilder(Class<VO> poClass, String[] queryProp) {
		this(poClass);
		this.select(queryProp);
	}

	public static <VO> SingleSqlBuilder<VO> create(Class<VO> poClass) {
		return new SingleSqlBuilder<VO>(poClass);
	}

	public static <VO> SingleSqlBuilder<VO> create(Class<VO> poClass, String... queryProp) {
		return new SingleSqlBuilder<VO>(poClass, queryProp);
	}

	public SingleSqlBuilder<VO> select(String fieldName) {
		this.select(fieldName, fieldName);
		return this;
	}

	public SingleSqlBuilder<VO> selectAlias(String fieldName, String alias) {
		Preconditions.checkArgument(StringUtils.isNoneBlank(alias), "alias can not be empty");
		this.select.add(getColumn(fieldName), alias);
		return this;
	}

	public SingleSqlBuilder<VO> select(String... fieldNames) {
		if (ArrayUtils.isNotEmpty(fieldNames)) {
			for (String fieldName : fieldNames) {
				this.select.add(getColumn(fieldName), fieldName);
			}
		}
		return this;
	}

	public SingleSqlBuilder<VO> group(String fieldName) {
		String column = getColumn(fieldName);
		if (this.groupBy.add(column)) {
			this.select.add(column, fieldName);
		}
		return this;
	}

	public SingleSqlBuilder<VO> groupByNames(String... fieldNames) {
		if (ArrayUtils.isNotEmpty(fieldNames)) {
			for (String fieldName : fieldNames) {
				this.group(fieldName);
			}
		}
		return this;
	}

	public SingleSqlBuilder<VO> distinctCount(String fieldName) {
		this.distinctCount(fieldName, "DIS_CNT_" + fieldName);
		return this;
	}

	public SingleSqlBuilder<VO> distinctCount(String fieldName, String alias) {
		Preconditions.checkArgument(StringUtils.isNoneBlank(alias), "alias can not be empty");
		String column = getColumn(fieldName);
		this.select.add("COUNT( DISTINCT " + column + ") ", alias);
		return this;
	}

	public SingleSqlBuilder<VO> max(String fieldName, String alias) {
		Preconditions.checkArgument(StringUtils.isNoneBlank(alias), "alias can not be empty");
		String column = getColumn(fieldName);
		this.select.add("MAX(" + column + ") ", alias);
		return this;
	}

	public SingleSqlBuilder<VO> max(String fieldName) {
		max(fieldName, "MAX_" + fieldName);
		return this;
	}

	public SingleSqlBuilder<VO> min(String fieldName, String alias) {
		Preconditions.checkArgument(StringUtils.isNoneBlank(alias), "alias can not be empty");
		String column = getColumn(fieldName);
		this.select.add("MIN(" + column + ") ", alias);
		return this;
	}

	public SingleSqlBuilder<VO> min(String fieldName) {
		min(fieldName, "MIN_" + fieldName);
		return this;
	}

	public SingleSqlBuilder<VO> count(String fieldName) {
		this.count(fieldName, "CNT_" + fieldName);
		return this;
	}

	public SingleSqlBuilder<VO> count(String fieldName, String alias) {
		Preconditions.checkArgument(StringUtils.isNoneBlank(alias), "alias can not be empty");
		String column = getColumn(fieldName);
		this.select.add("COUNT(" + column + ") ", alias);
		return this;
	}

	public SingleSqlBuilder<VO> having(String having) {
		this.having = having;
		return this;
	}

	public SingleSqlBuilder<VO> sum(String fieldName) {
		this.sum(fieldName, "SUM_" + fieldName);
		return this;
	}

	public SingleSqlBuilder<VO> sum(String fieldName, String alias) {
		Preconditions.checkArgument(StringUtils.isNoneBlank(alias), "alias can not be empty");
		String column = getColumn(fieldName);
		this.select.add("SUM(" + column + ") ", alias);
		return this;
	}

	public SingleSqlBuilder<VO> distinct(String fieldName) {
		this.distinct(fieldName, "DIST_" + fieldName);
		return this;
	}

	public SingleSqlBuilder<VO> distinct(String fieldName, String alias) {
		Preconditions.checkArgument(StringUtils.isNoneBlank(alias), "alias can not be empty");
		String column = getColumn(fieldName);
		this.select.add("DISTINCT(" + column + ")  ", alias);
		return this;
	}

	public SingleSqlBuilder<VO> add(@NonNull Expression expression) {
		this.where.addCondition(expression);
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> eq(String fieldName, T value) {
		this.add(Expressions.eq(fieldName, value));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> ne(String fieldName, T value) {
		this.add(Expressions.ne(fieldName, value));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> lt(String fieldName, T value) {
		this.add(Expressions.lt(fieldName, value));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> le(String fieldName, T value) {
		this.add(Expressions.le(fieldName, value));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> gt(String fieldName, T value) {
		this.add(Expressions.gt(fieldName, value));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> ge(String fieldName, T value) {
		this.add(Expressions.ge(fieldName, value));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> or(@NonNull Expression leftCondition,
			@NonNull Expression rightCondition) {
		this.where.addCondition(Expressions.or(leftCondition, rightCondition));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> or(@NonNull Expression... conditions) {
		this.where.addCondition(Expressions.or(conditions));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> and(@NonNull Expression leftCondition,
			@NonNull Expression rightCondition) {
		this.where.addCondition(Expressions.and(leftCondition, rightCondition));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> and(@NonNull Expression... conditions) {
		this.where.addCondition(Expressions.and(conditions));
		return this;
	}

	public SingleSqlBuilder<VO> in(String fieldName, Collection<? extends Serializable> values) {
		this.where.addCondition(Expressions.in(fieldName, values));
		return this;
	}

	public SingleSqlBuilder<VO> tupleIn(List<String> fieldNames, Collection<List<Object>> values) {
		this.where.addCondition(Expressions.tupleIn(fieldNames, values));
		return this;
	}

	public SingleSqlBuilder<VO> notin(String fieldName, Collection<? extends Serializable> values) {
		this.where.addCondition(Expressions.notin(fieldName, values));
		return this;
	}

	public SingleSqlBuilder<VO> dateformat(String fieldName, String format, String value) {
		this.where.addCondition(Expressions.dateformat(fieldName, format, value));
		return this;
	}

	public SingleSqlBuilder<VO> between(String fieldName, Comparable<?> startValue, Comparable<?> endValue) {
		this.where.addCondition(Expressions.between(fieldName, startValue, endValue));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> like(String fieldName, T value) {
		this.where.addCondition(Expressions.like(fieldName, value));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> like(String fieldName, T value, MatchMode matchMode) {
		this.where.addCondition(Expressions.like(fieldName, value, matchMode));
		return this;
	}

	public SingleSqlBuilder<VO> isNull(String fieldName) {
		this.where.addCondition(Expressions.isNull(fieldName));
		return this;
	}

	public SingleSqlBuilder<VO> isNotNull(String fieldName) {
		this.where.addCondition(Expressions.isNotNull(fieldName));
		return this;
	}

	public SingleSqlBuilder<VO> desc(String fieldName) {
		return desc(fieldName, false);
	}

	/**
	 * 倒序
	 * 
	 * @param fieldName
	 * @param gbkOrder
	 *            true表示将字段转换成GBK再进行排序 CONVERT ( column USING GBK)
	 * @return
	 */
	public SingleSqlBuilder<VO> desc(String fieldName, boolean gbkOrder) {
		this.orders.add(Order.desc(fieldName, gbkOrder));
		return this;
	}

	public SingleSqlBuilder<VO> desc(String... fieldName) {
		this.orders.add(Order.desc(fieldName));
		return this;
	}

	public SingleSqlBuilder<VO> asc(String fieldName) {
		return asc(fieldName, false);
	}

	public SingleSqlBuilder<VO> asc(String... fieldName) {
		this.orders.add(Order.asc(fieldName));
		return this;
	}

	public void cleanOrder() {
		this.orders.clear();
	}

	/**
	 * 
	 * @param fieldName
	 * @param gbkOrder
	 *            true表示将字段转换成GBK再进行排序 CONVERT ( column USING GBK)
	 * @return
	 */
	public SingleSqlBuilder<VO> asc(String fieldName, boolean gbkOrder) {
		this.orders.add(Order.asc(fieldName, gbkOrder));
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> orderByField(String fieldName, Collection<T> values) {
		this.orderByField = new OrderByField(fieldName, values);
		return this;
	}

	private String getColumn(String fieldName) {
		return ColumnUtil.getColumnName(fieldName, this.context.getFieldMapColumn(), this.context.getColumnMapField());
	}

	public <T extends Serializable> SingleSqlBuilder<VO> buildSelectById(T id) {
		if (id == null) {
			throw new IllegalArgumentException("must have id value.");
		}
		if (this.idColumn == null) {
			throw new UnsupportedOperationException("can not found id column,please place @Id in id get method");
		}
		this.eq(this.idColumn.getFieldName(), id);
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> buildSelectById(T[] ids) {
		if (ids == null || ids.length == 0) {
			throw new IllegalArgumentException("must have id value.");
		}
		if (this.idColumn == null) {
			throw new UnsupportedOperationException("can not found id column,please place @Id in id get method");
		}
		if (ids.length == 1) {
			this.eq(this.idColumn.getFieldName(), ids[0]);
		} else {
			this.in(this.idColumn.getFieldName(), Lists.newArrayList(ids));
		}
		return this;
	}

	public <T extends Serializable> SingleSqlBuilder<VO> buildSelectById(Collection<T> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new IllegalArgumentException("must have id value.");
		}
		if (this.idColumn == null) {
			throw new UnsupportedOperationException("can not found id column,please place @Id in id get method");
		}
		this.in(this.idColumn.getFieldName(), ids);
		return this;
	}

	public String toFrom() {
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM ").append(this.from.toSql(context)).append(" ");
		return sql.toString();
	}

	public String toWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(this.where.toSql(context)).append(" ");
		return sql.toString();
	}

	public String toSql(boolean forUpdate) {
		if (this.select.isEmpty()) {
			for (String column : context.getFieldMapColumn().keySet()) {
				this.select(column);
			}
		}
		StringBuilder sql = new StringBuilder(this.select.toSql());
		sql.append(" FROM ").append(this.from.toSql(context)).append(" ");
		sql.append(this.where.toSql(context)).append(" ");

		sql.append(this.groupBy.toSql(context));

		if (StringUtils.isNoneBlank(having)) {
			sql.append(" ").append(having).append(" ");
		}

		if (CollectionUtils.isNotEmpty(orders)) {
			sql.append(Order.toSql(orders, context));
		}
		if (this.orderByField != null) {
			if (this.orderByField != null) {
				sql.append(this.orderByField.toFieldSql(context));
			} else {
				sql.append(this.orderByField.toSql(context));
			}
		}

		if (this.page != null) {
			sql.append(" LIMIT ").append(this.page.firstNum()).append(",").append(this.page.getPageSize());
		} else if (this.maxSize != null && this.maxSize > 0) {
			sql.append(" LIMIT ");
			sql.append(start).append(",");
			sql.append(this.maxSize);
		} else if (forUpdate) {
			sql.append(" FOR UPDATE");
		}

		String sqlStr = sql.toString();
		log.debug("query sql:{}", sqlStr);
		return sqlStr;
	}

	public String toSql() {
		return toSql(false);
	}

	public Map<String, Object> collectConditionValue() {
		Map<String, Object> paramMap = this.where.collectParamValue(context);
		if (this.orderByField != null) {
			paramMap.putAll(this.orderByField.getParamNameValueMap());
		}
		return paramMap;
	}

	public String toCountSql() {
		StringBuilder sql = new StringBuilder("SELECT count(1)");
		sql.append(" FROM ").append(this.from.toSql(context)).append(" ");
		sql.append(this.where.toSql(context)).append(" ");
		String groupBySql = this.groupBy.toSql(context);
		if (StringUtils.isNoneBlank(groupBySql)) {
			sql.insert(0, "SELECT COUNT(1) FROM ( ");
			sql.append(groupBySql);
			if (StringUtils.isNoneBlank(having)) {
				sql.append(" HAVING ").append(having);
			}
			sql.append(" ) tmp ");
		}
		log.debug("query count sql:{}", sql);
		return sql.toString();
	}

	public String toInsertSql(String... insertProperties) {
		return this.toBatchInsertSql(1, insertProperties);
	}

	/**
	 * 批量保存,不支持自动过滤空值
	 * 
	 * @param size
	 * @param insertProperties
	 * @return
	 */
	public String toBatchInsertSql(int size, String... insertProperties) {
		StringBuilder sql = new StringBuilder("INSERT INTO");

		sql.append(" ").append(this.from.toSql(context)).append(" (");

		Set<String> columns = Sets.newLinkedHashSet();
		List<String> objectNameValues = Lists.newArrayList();
		if (ArrayUtils.isEmpty(insertProperties)) {
			insertProperties = context.getFieldMapColumn().keySet().toArray(new String[] {});
		}
		for (int i = 0; i < size; i++) {
			List<String> objectNameValue = Lists.newArrayList();
			for (String prop : insertProperties) {
				String column = getColumn(prop);
//				if (column.equals(idColumn.getColumnName()) && idColumn.isAuto()) {
//					continue;
//				}
				columns.add(column);
				String propName = ":" + prop;
				if (i > 0) {
					propName += "_" + i;
				}
				objectNameValue.add(propName);
			}
			objectNameValues.add("(" + StringUtils.join(objectNameValue, ", ") + ")");
		}
		sql.append(StringUtils.join(columns, ", ")).append(")");
		sql.append(" VALUES ").append(StringUtils.join(objectNameValues, ", ")).append(" ");
		String sqlStr = sql.toString();
		log.debug("generate insert sql:{}", sqlStr);
		return sql.toString();
	}

	public String toDeleteSqlByCondition() {
		StringBuilder sql = new StringBuilder("DELETE FROM");
		sql.append(" ").append(this.from.toSql(context)).append(" ");

		sql.append(where.toSql(context));
		String sqlStr = sql.toString();
		log.debug("generate delete sql:{}", sqlStr);

		return sqlStr;
	}

	public String toUpdateSql(String... updateProperties) {
		StringBuilder sql = new StringBuilder("UPDATE");
		sql.append(" ").append(this.from.toSql(context)).append(" SET ");

		List<String> setCondition = Lists.newArrayList();
		boolean updateAll = false;
		if (ArrayUtils.isEmpty(updateProperties)) {
			updateAll = true;
			updateProperties = context.getFieldMapColumn().keySet().toArray(new String[] {});
		}

		if (ArrayUtils.isNotEmpty(updateProperties)) {
			for (String prop : updateProperties) {
				if (prop.equals(idColumn.getFieldName()) && updateAll) {
					continue;
				}
				setCondition.add(getColumn(prop) + "=:" + prop);
			}
		}
		sql.append(" ").append(StringUtils.join(setCondition, ", "));
		sql.append(" ").append(this.where.toSql(context));
		String sqlStr = sql.toString();
		log.debug("generate update sql:{}", sqlStr);

		return sqlStr;
	}

	/**
	 * getter and setters
	 */
	public void addOrder(Order order) {
		if (order != null) {
			this.orders.add(order);
		}
	}

	public static org.slf4j.Logger getLog() {
		return log;
	}

	public TableDefine getTableDefine() {
		return tableDefine;
	}

	public Class<VO> getPoClass() {
		return poClass;
	}

	public ColumnDefine getIdColumn() {
		return idColumn;
	}

	public PageDto getPage() {
		return page;
	}

	public int getStart() {
		return start;
	}

	public Select getSelect() {
		return select;
	}

	public From getFrom() {
		return from;
	}

	public Where getWhere() {
		return where;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public OrderByField getOrderByField() {
		return orderByField;
	}

	public GroupBy getGroupBy() {
		return groupBy;
	}

	public String getHaving() {
		return having;
	}

	public boolean isSetDefaultVal() {
		return setDefaultVal;
	}

	public void setTableDefine(TableDefine tableDefine) {
		this.tableDefine = tableDefine;
	}

	public void setPoClass(Class<VO> poClass) {
		this.poClass = poClass;
	}

	public void setIdColumn(ColumnDefine idColumn) {
		this.idColumn = idColumn;
	}

	public void setPage(PageDto page) {
		this.page = page;
	}

	public void setContext(SqlBuilderContext context) {
		this.context = context;
	}

	public void setSelect(Select select) {
		this.select = select;
	}

	public void setFrom(From from) {
		this.from = from;
	}

	public void setWhere(Where where) {
		this.where = where;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setOrderByField(OrderByField orderByField) {
		this.orderByField = orderByField;
	}

	public void setGroupBy(GroupBy groupBy) {
		this.groupBy = groupBy;
	}

	public void setHaving(String having) {
		this.having = having;
	}

	public void setSetDefaultVal(boolean setDefaultVal) {
		this.setDefaultVal = setDefaultVal;
	}

	
	
}
