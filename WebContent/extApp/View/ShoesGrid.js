Ext.define('inlineEditor.View.ShoesGrid', {

	extend : 'Ext.grid.Panel',
	alias : 'widget.ShoesGrid',
	id : 'ShoesGrid',
	width : '50%',
	height : 450,
	selType : 'checkboxmodel',
	selModel : {
		checkOnly : true,
		mode : 'MULTI'
	},
	viewConfig : {
		emptyText : 'No Data To Display'
	},
	title : 'ShoesGrid',
	store : 'Shoes',

	columns : [{
				text : "Shoes ID",
				dataIndex : 'shoesId',
				sortable : true
			}, {
				text : "Shoes Name",
				flex : 1,
				dataIndex : 'shoesName',
				editor : {
					allowBlank : true
				}
			}, {
				text : "Series",
				flex : 1,
				dataIndex : 'series',
				editor : {
					allowBlank : true
				}
			}, {
				text : "Category",
				flex : 1,
				dataIndex : 'category',
				editor : {
					allowBlank : true
				}
			}, {
				text : "Price",
				flex : 1,
				dataIndex : 'price',
				editor : {
					allowBlank : true
				}
			}, {
				text : "Brand ID",
				dataIndex : 'brandId',
				sortable : true

			}],
	dockedItems : [{
		xtype : 'toolbar',
		items : [{
			text : 'Add',
			itemId : 'add',
			iconCls : 'icon-add',
			disabled : true,
			handler : function() {

				this.fireEvent('createModel', this.findParentByType('grid'),
						this.findParentByType('grid').store);

			}
		}, '-', {
			itemId : 'delete',
			text : 'Delete',
			iconCls : 'icon-delete',
			handler : function() {
				this.fireEvent('deleteModel', this.findParentByType('grid'),
						this.findParentByType('grid').store);
			}

		}, '-', {
			text : 'Load',
			iconCls : 'icon-refresh',
			handler : function() {
				this.fireEvent('loadModel', this.findParentByType('grid'), this
								.findParentByType('grid').store)
			}
		}]
	}],
	plugins : [Ext.create('Ext.grid.plugin.RowEditing', {
		clicksToEdit : 2,
		ptype : 'rowediting',
		pluginId : 'rowediting',
		autoCancel : true,
		listeners : {
			edit : function(editor, e, eOpts) {// e element
				e.record.save({
							callback : function(record, operation) {
								var obj = Ext
										.decode(operation._response.responseText)
								e.record.set(e.record.idField.name,
										obj[e.record.idField.name]);
							}
						});
			},
			canceledit : function(editor, e, eOpts) {
				if (e.record.id == "") {
					e.record.store.remove(e.record);
				}
			}
		}
	})]

});