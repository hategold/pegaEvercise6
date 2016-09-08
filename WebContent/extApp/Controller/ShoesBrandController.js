Ext.define('inlineEditor.Controller.ShoesBrandController', {
	extend : 'Ext.app.Controller',

	init : function() {
		this.control({
					'BrandGrid' : {
						rowclick : this.onGridRowClick,
						edit : this.onRowEdit,
						canceledit : this.onRowEditCancel
					},
					'BrandGrid button' : {
						createModel : this.onCreateModel,
						deleteModel : this.onDeleteModel,
						loadModel : this.onLoadModel
					},
					'ShoesGrid button' : {
						createModel : this.onCreateModel,
						deleteModel : this.onDeleteModel,
						loadModel : this.onLoadModel
					}
				});
	},
	onLoadModel : function(grid, store) {
		this.cancelEditor(grid)

		store.load();

	},

	onDeleteModel : function(grid, store) {
		// delete selected rows if selModel is checkboxmodel
		this.cancelEditor(grid);

		var selectedRows = grid.getView().getSelectionModel().getSelection();
		if (selectedRows.length) {
			Ext.Msg.confirm("Delete Confirm",
					"Are you sure ? this action cannot be reversed!", function(
							ans) {
						if (ans == "yes") {
							store.remove(selectedRows);
							store.sync();
						}
					})
		} else {
			Ext.Msg.alert('Status',
					'Please select at least one record to delete!');
		}

	},

	onCreateModel : function(grid, store) {
		var view = grid.getView();
		var model = Ext.create(store.model);

		this.setModelByFilterConfig(model, store);

		model.setId("");
		store.add(model);
		this.cancelEditor(grid);
		view.editingPlugin.startEdit(model, 0);
	},

	onGridRowClick : function(grid, record) {
		var shoesStore = Ext.getStore('Shoes');
		var shoesGrid = Ext.getCmp('ShoesGrid');
		shoesGrid.selModel.deselectAll();
		shoesGrid.down('#add').setDisabled(false);
		shoesStore.filter(record.idField.name, record.id);

	},

	onRowEdit : function(editor, e, eOpts) {// e element
		e.record.save({
			callback : function(record, operation) {
				var obj = Ext.decode(operation._response.responseText)

				e.record.set(e.record.idField.name, obj[e.record.idField.name]);
			}
		});
	},

	onRowEditCancel : function(editor, e, eOpts) {
		if (e.record.id == "") {
			e.record.store.remove(e.record);
		}
	},

	cancelEditor : function(grid) {
		grid.getView().editingPlugin.cancelEdit();
	},

	setModelByFilterConfig : function(model, store) {

		if (store.filters) {
			var i
			for (i = 0; i < store.filters.items.length; i++) {
				model.set(store.filters.items[i].initialConfig.property,
						store.filters.items[i].initialConfig.value)
			}
		}

	}
});