import {ACTION, GlobalContextState} from "../../general/GeneralTypes";
import {Credential} from "./TypeDefs";
import {FN_NOTHING} from "../../general/GeneralValues";

export const initialGlobalContextState: GlobalContextState = {
	loginUser: undefined,
	login: FN_NOTHING,

	alertOpen: false,
	alert: FN_NOTHING,
}

export const GlobalContextReducer = (value: GlobalContextState, action: ACTION): GlobalContextState => {
	switch(action.type) {
		case 'LOGIN':
			return { ...value };
	}

	return value;
}