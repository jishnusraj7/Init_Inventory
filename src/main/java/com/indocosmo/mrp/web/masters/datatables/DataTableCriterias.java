package com.indocosmo.mrp.web.masters.datatables;

import java.util.List;
import java.util.Map;

public class DataTableCriterias {
		private int draw;
	    private int start;
	    private int length;
	    private int filter;

	    private Map<SearchCriterias, String> search;

	    private List<Map<ColumnCriterias, String>> columns;

	    private List<Map<OrderCriterias, String>> order;
	    
	    private List<Integer> filterList;
	    

	    public enum SearchCriterias {
	        value,
	        regex
	    }
	    
	    public enum OrderCriterias {
	        column,
	        dir
	    }
	    public enum ColumnCriterias {
	        data,
	        name,
	        searchable,
	        orderable,
	        searchValue,
	        searchRegex
	    }
		public int getDraw() {
			return draw;
		}
		public void setDraw(int draw) {
			this.draw = draw;
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		
		/**
		 * @return the search
		 */
		public Map<SearchCriterias, String> getSearch() {
			return search;
		}
		/**
		 * @param search the search to set
		 */
		public void setSearch(Map<SearchCriterias, String> search) {
			this.search = search;
		}
		
		/**
		 * @return the filter
		 */
		public int getFilter() {
			return filter;
		}
		/**
		 * @param filter the filter to set
		 */
		public void setFilter(int filter) {
			this.filter = filter;
		}
		public List<Map<ColumnCriterias, String>> getColumns() {
			return columns;
		}
		public void setColumns(List<Map<ColumnCriterias, String>> columns) {
			this.columns = columns;
		}
		public List<Map<OrderCriterias, String>> getOrder() {
			return order;
		}
		public void setOrder(List<Map<OrderCriterias, String>> order) {
			this.order = order;
		}
		/**
		 * @return the filterList
		 */
		public List<Integer> getFilterList() {
			return filterList;
		}
		/**
		 * @param filterList the filterList to set
		 */
		public void setFilterList(List<Integer> filterList) {
			this.filterList = filterList;
		}
	    
}
