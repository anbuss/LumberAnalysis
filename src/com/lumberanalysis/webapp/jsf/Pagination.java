/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.jsf;

import com.logviewer.log.view.LogRowForView;
import com.lumberanalysis.webapp.util.DataAccessController;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author samidoss
 */
public class Pagination {
    private DataAccessController dataAccess = new DataAccessController();
    private DataModel<LogRowForView> dataModel;
    private int rowsPerPage = 5;
    private LogRowForView current;
    private int selectedItemIndex = -1;
    private Paginator paginator;
    
     public Paginator getPaginator() {
        if (paginator == null) {
            paginator = new Paginator(rowsPerPage) {

                @Override
                public int getItemsCount() {
                    return dataAccess.count(LogRowForView.class);
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(dataAccess.findRange(LogRowForView.class, getPageFirstItem(), getPageFirstItem() + getPageSize()));
                }
            };
        }

        return paginator;

    }
}
