package com.zf.dto.easyui;

import java.util.List;

public class Datagrid<T> {
    /**
     * 数据总记录条数
     */
    private Integer total;

    /**
     * 数据内容
     */
    private List<T> rows;

    public Datagrid(List<T> rows) {
        super();
        this.rows = rows;
        this.total = rows.size();
    }

    public Datagrid(List<T> rows, int total) {
        super();
        this.rows = rows;
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
