import {ACTION, AuthResponse, GlobalContextState} from "../../general/GeneralTypes";
import {Credential} from "./TypeDefs";
import {FN_NOTHING} from "../../general/GeneralValues";
import {MessageProps} from "../../components/base/messages/TypeDefs";

const getLoginMember = () => {
	const token = localStorage.getItem('token');
	if(!token) return undefined;
	try {
		return JSON.parse(localStorage.getItem('loginMember') || '');
	} catch(error) {
		console.log('there is token but no login member data');
		return undefined;
	}
}

export const initialGlobalContextState: GlobalContextState = {
	loginMember: getLoginMember(),
	login: FN_NOTHING,

	alertOpen: false,
	alert: FN_NOTHING,
	alertTitle: '',
	alertMessage: '',
	alertClose: FN_NOTHING
}

export const GlobalContextReducer = (value: GlobalContextState, action: ACTION): GlobalContextState => {
	switch(action.type) {
		case 'LOGIN_SUCCESS':
			debugger;
			const authResponse = action.payload as AuthResponse;
			localStorage.setItem('loginMember', JSON.stringify(authResponse.member));
			localStorage.setItem('loginToken', authResponse.token);
			return {
				...value,
				// loginMember: authResponse.member
			};

		case 'ALERT':
			const alertMessageProps = action.payload as MessageProps;
			return {
				...value,
				alertOpen: true,
				alertTitle: alertMessageProps.title || '',
				alertMessage: alertMessageProps.message || ''
			}
		case 'ALERT_CLOSE':
			// todo CHK01 메시지를 클리어 해주고 싶지만 완전히 사라지기 전에 글자부터 바뀌어서 남겨둔다
			return { ...value, alertOpen: false, /*alertTitle: '', alertMessage: ''*/ }
	}

	return value;
}