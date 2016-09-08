Ext.define('inlineEditor.View.BrandGrid', {

			extend : 'Ext.grid.Panel',
			alias : 'widget.BrandGrid',
			id : 'BrandGrid',
			// config : {},
			// constructor : function(config) {
			// // this.initConfig(config);
			// return this.callParent(arguments);
			// },
			width : '50%',
			height : 450,
			selType : 'checkboxmodel',
			selModel : {
				checkOnly : true,
				mode : 'MULTI'

			},

			title : 'BrandGrid',

			store : 'Brand',
			columns : [{
						text : "Brand ID",
						dataIndex : 'brandId',
						sortable : true,
						sorter : {
							sorterFn : function(a, b) {
								var diff = a.id - b.id;
								return (diff > 0) ? 1 : ((diff == 0) ? 0 : -1);
							}
						}
					}, {
						text : "Brand Name",
						flex : 1,
						dataIndex : 'brandName',
						editor : {
							allowBlank : false
						}
					}, {
						text : "Website",
						flex : 1,
						dataIndex : 'website',
						editor : {
							allowBlank : true
						}
					}, {
						text : "Country",
						flex : 1,
						dataIndex : 'country',
						editor : {
							allowBlank : false,
							xtype : 'combobox',
							store : 'Country',
							queryMode : 'local',
							displayField : 'fullname',
							valueField : 'code'
						}
					}],

			dockedItems : [{
				xtype : 'toolbar',
				items : [{
					text : 'Add',
					iconCls : 'icon-add',
					handler : function() {
						this.fireEvent('createModel', this
										.findParentByType('grid'), this
										.findParentByType('grid').store);

					}
				}, '-', {
					itemId : 'delete',
					text : 'Delete',
					iconCls : 'icon-delete',
					handler : function() {

						this.fireEvent('deleteModel', this
										.findParentByType('grid'), this
										.findParentByType('grid').store);
					}

				}, '-', {
					text : 'Load',
					iconCls : 'icon-refresh',
					handler : function() {
						this.fireEvent('loadModel', this
										.findParentByType('grid'), this
										.findParentByType('grid').store)
					}
				}]
			}],
			plugins : ['rowediting']
		});