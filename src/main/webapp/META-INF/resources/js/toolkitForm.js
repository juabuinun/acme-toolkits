//ADDING WTHIS FOR TESTS
var available_components_arr = new Array();
var binded_components_arr = new Array();

var available_tools_arr = new Array();
var binded_tools_arr = new Array();

$(document).ready(function() {
	$(".btn-tool-select").unbind("click").click(function() {
		$(this).toggleClass("active");
		console.log("select tool "+ $(this));
		return false;
	});
});

$(document).ready(function() {
	$(".btn-component-select").unbind("click").click(function() {
		$(this).toggleClass("active");
		console.log("select component "+ $(this));
		return false;
	});
});

function removeItemFromArray(arr, item) {
	for (var i = arr.length - 1; i >= 0; --i) {
		if (arr[i].id == item.id) {
			arr.splice(i, 1);
		}
	}
}

function fetchItemObject(elm) {
	var res = { id: $(elm).attr("id"), quantity: $("> .qty-input", elm).val() }
	return res;
}

function refreshComponentsInput() {
	$("#available_components_input").val(JSON.stringify(available_components_arr));
	$("#binded_components_input").val(JSON.stringify(binded_components_arr));
}

function refreshToolsInput() {
	$("#available_tools_input").val(JSON.stringify(available_tools_arr));
	$("#binded_tools_input").val(JSON.stringify(binded_tools_arr));
}

$(document).ready(function() {
	$(function() {
		//load arrays
		$("#available_components_collection").children("div").children("label").each(function() {
			var item = fetchItemObject($(this));
			available_components_arr.push(item);
		})
		$("#binded_components_collection").children("div").children("label").each(function() {
			var item = fetchItemObject($(this));
			binded_components_arr.push(item);
		})
		refreshComponentsInput();
	});
});

$(document).ready(function() {
	$("#btn-bind-selected-components").click(function() {
		//move selected to binded box
		$("#available_components_collection").children("div").children("label").each(function() {
			if ($(this).hasClass("active")) {

				$(this).toggleClass("active");
				$(this).parent().detach().appendTo("#binded_components_collection");
				$("> .qty-input", this).prop("readonly", true);

				var item = fetchItemObject($(this));
				removeItemFromArray(available_components_arr, item);
				binded_components_arr.push(item);
			}
		})
		refreshComponentsInput();
	});
});
$(document).ready(function() {
	$("#btn-bind-all-components").click(function() {
		//move selected to binded box
		$("#available_components_collection").children("div").children("label").each(function() {
			if ($(this).hasClass("active")) {
				$(this).toggleClass("active");
			}
			$("> .qty-input", this).prop("readonly", true);
			$(this).parent().detach().appendTo("#binded_components_collection");
			binded_components_arr.push.apply(binded_components_arr, available_components_arr);
			available_components_arr = [];
		})
		refreshComponentsInput();
	});
});
$(document).ready(function() {
	$("#btn-unbind-selected-components").click(function() {
		//move selected to unbinded box
		$("#binded_components_collection").children("div").children("label").each(function() {
			if ($(this).hasClass("active")) {
				$(this).toggleClass("active");
				$(this).parent().detach().appendTo("#available_components_collection");
				$("> .qty-input", this).prop("readonly", false);
				var item = fetchItemObject($(this));
				removeItemFromArray(binded_components_arr, item);
				available_components_arr.push(item);
			}
		})
		refreshComponentsInput();
	});
});
$(document).ready(function() {
	$("#btn-unbind-all-components").click(function() {
		//move selected to binded box
		$("#binded_components_collection").children("div").children("label").each(function() {
			if ($(this).hasClass("active")) {
				$(this).toggleClass("active");
			}
			$("> .qty-input", this).prop("readonly", false);
			$(this).parent().detach().appendTo("#available_components_collection");
			available_components_arr.push.apply(available_components_arr, binded_components_arr);
			binded_components_arr = [];
		})
		refreshComponentsInput();
	});
});


//TOOL STUFF

$(document).ready(function() {
	$(function() {
		//load arrays
		$("#available_tools_collection").children("div").children("label").each(function() {
			var item = fetchItemObject($(this));
			available_tools_arr.push(item);
		})
		$("#binded_tools_collection").children("div").children("label").each(function() {
			var item = fetchItemObject($(this));
			binded_tools_arr.push(item);
		})
		refreshToolsInput();
	});
});
$(document).ready(function() {
	$("#btn-bind-selected-tools").click(function() {
		//move selected to binded box
		$("#available_tools_collection").children("div").children("label").each(function() {
			if ($(this).hasClass("active")) {

				$(this).toggleClass("active");
				$(this).parent().detach().appendTo("#binded_tools_collection");
				$("> .qty-input", this).prop("readonly", true);

				var item = fetchItemObject($(this));
				removeItemFromArray(available_tools_arr, item);
				binded_tools_arr.push(item);
			}
		})
		refreshToolsInput();
	});
});
$(document).ready(function() {
	$("#btn-bind-all-tools").click(function() {
		//move selected to binded box
		$("#available_tools_collection").children("div").children("label").each(function() {
			if ($(this).hasClass("active")) {
				$(this).toggleClass("active");
			}
			$("> .qty-input", this).prop("readonly", true);
			$(this).parent().detach().appendTo("#binded_tools_collection");
			binded_tools_arr.push.apply(binded_tools_arr, available_tools_arr);
			available_tools_arr = [];
		})
		refreshToolsInput();
	});
});
$(document).ready(function() {
	$("#btn-unbind-selected-tools").click(function() {
		//move selected to unbinded box
		$("#binded_tools_collection").children("div").children("label").each(function() {
			if ($(this).hasClass("active")) {
				$(this).toggleClass("active");
				$(this).parent().detach().appendTo("#available_tools_collection");

				$("> .qty-input", this).prop("readonly", false);
				var item = fetchItemObject($(this));
				removeItemFromArray(binded_tools_arr, item);
				available_tools_arr.push(item);
			}
		})
		refreshToolsInput();
	});
});
$(document).ready(function() {
	$("#btn-unbind-all-tools").click(function() {
		//move selected to binded box
		$("#binded_tools_collection").children("div").children("label").each(function() {
			if ($(this).hasClass("active")) {
				$(this).toggleClass("active");
			}
			$("> .qty-input", this).prop("readonly", false);
			$(this).parent().detach().appendTo("#available_tools_collection");
			available_tools_arr.push.apply(available_tools_arr, binded_tools_arr);
			binded_tools_arr = [];
		})
		refreshToolsInput();
	});
});

