/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.logviewer;

import com.logviewer.log.ConstructPOJO;
import com.logviewer.log.view.ApplicationNodeForView;
import com.lumberanalysis.webapp.action.BasePage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.lang.StringUtils;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author samidoss
 */
public abstract class TreeBackingBean extends BasePage {

    private TreeNode root;
    private TreeNode[] selectedNodes;

    private void pushToTree(ApplicationNodeForView tree, ApplicationNodeForView newObject, int hirPos) {

        String id = newObject.getAppGroupName(hirPos);
        getLog().info("For Item :" + id + "(" + newObject.getId() + ")");
        ApplicationNodeForView temp = tree.getChildbyKey(id);
        if (temp != null) {
            pushToTree(temp, newObject, hirPos + 1);
            //return;
        } else {
            tree.addChild(id, newObject);
        }
    }

    protected void appList() {
        ConstructPOJO pojo = new ConstructPOJO();

        //  root.setSelectable(false);
        Hashtable<String, ApplicationNodeForView> tree = new Hashtable<String, ApplicationNodeForView>();
        List<ApplicationNodeForView> appNameObj = pojo.initTree();
        for (int i = 0; i < menuLevel; i++) {
            for (ApplicationNodeForView appName : appNameObj) {
                int hirPos = 0;
                String id = appName.getAppGroupName(hirPos++);
                if (i != StringUtils.countMatches(appName.getId(), ".")) {
                    continue;
                }
                ApplicationNodeForView t = tree.get(id);

                if (t != null) {
                    id = appName.getAppGroupName(hirPos);
                    if (id != null) {
                        pushToTree(t, appName, hirPos);
                    }

                } else {
                    tree.put(id, appName);
                }
            }
        }
        //For Screen.
        DefaultTreeNode top = new DefaultTreeNode("HOME", new ApplicationNodeForView("Application's", "Application's"), null);
        List<TreeNode> child = new ArrayList<TreeNode>();
        for (ApplicationNodeForView appName : tree.values()) {
           // drillLevel=1;
            child.add(constructBranchAndLeaf(appName, 0));
        }
        top.setChildren(child);
        root = top;
    }
    
    
    private int menuLevel=3;
    
    private DefaultTreeNode constructBranchAndLeaf(ApplicationNodeForView branch, int drillLevel) {
        DefaultTreeNode node = new DefaultTreeNode();
        node.setData(branch);

        //node.setType(branch.getCls());
        if (!branch.getChild().isEmpty()) {
            List<TreeNode> child = new ArrayList<TreeNode>();
            for (ApplicationNodeForView appName : branch.getChild().values()) {
                if (drillLevel <= menuLevel)
                    child.add(constructBranchAndLeaf(appName, drillLevel+1));
            }
            node.setExpanded(false);
            node.setChildren(child);
            branch.setLeaf(false);
        } else {
            branch.setLeaf(true);
            node.setExpanded(false);
            ;//it's a Leaf.
        }
        return node;
    }
    public TreeNode getRoot() {
        return root;
    }
    ArrayList<TreeNode> selectedNodeList;

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }
    @Deprecated
    public void addSelectedNode(TreeNode sn) {
        if (this.selectedNodes == null) {
            this.selectedNodeList = new ArrayList<TreeNode>();
        }
        this.selectedNodeList.add(sn);
        this.selectedNodes = (TreeNode[]) this.selectedNodeList.toArray();

        if (selectedNodes != null && selectedNodes.length > 0) {
            StringBuilder builder = new StringBuilder();

            for (TreeNode node : selectedNodes) {
                builder.append(node.getData().toString());
                builder.append("<br />");
            }
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());

            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void setremoveSelectedNodes(TreeNode sn) {
        if (this.selectedNodes == null) {
            this.selectedNodeList = new ArrayList<TreeNode>();
        }
        this.selectedNodeList.remove(sn);
        this.selectedNodes = (TreeNode[]) this.selectedNodeList.toArray();

        if (selectedNodes != null && selectedNodes.length > 0) {
            StringBuilder builder = new StringBuilder();

            for (TreeNode node : selectedNodes) {
                builder.append(node.getData().toString());
                builder.append("<br />");
            }
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());

            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

     protected List<String> getSelectedAppLeafList() {
        ArrayList<String> r = new ArrayList<String>();
        TreeNode[] l = getSelectedNodes();
        if (l != null) {
            for (TreeNode appObj : l) {
                ApplicationNodeForView a = (ApplicationNodeForView) appObj.getData();
                if (a.isLeaf()) {
                    r.add(a.getId());
                }
            }
        }
        return r;
    }
     
    public void onNodeSelect(NodeSelectEvent e) {
        getSearchCriteria();
        //  this.addSelectedNode(e.getTreeNode());
    }

    public void onNodeExpand(NodeExpandEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().toString());

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        getSearchCriteria();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "onNodeUnselect", event.getTreeNode().toString());
        //   this.setremoveSelectedNodes(event.getTreeNode());
    }
    public abstract void getSearchCriteria();
}
