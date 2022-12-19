import {ACTION, GlobalContextState} from "../../general/GeneralTypes";

export const initialGlobalContextState: GlobalContextState = {
	loginUser: undefined
}

export const GlobalContextReducer = (action: ACTION, value: GlobalContextState): GlobalContextState => {
	switch(action.type) {
		case 'LOGIN':
			return { ...value };
	}

	return value;
}