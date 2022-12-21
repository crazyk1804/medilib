import React, {createContext, ReactNode, useReducer} from 'react';
import {GlobalContextReducer, initialGlobalContextState} from "./GlobalContextReducer";
import {AuthResponse, ExceptionResponse, ReactParent, ServerResponse} from "../../general/GeneralTypes";
import {doFetch} from "../../util/web-util";
import {Credential} from "./TypeDefs";

export const GlobalContext = createContext(initialGlobalContextState);

export const GlobalContextProvider = ({children}: ReactParent) => {
	const [value, dispatch] = useReducer(GlobalContextReducer, initialGlobalContextState);

	// const api = async <P, R>(url: string, data: P) : R => {
	// 	const response  = await doFetch<P, ServerResponse<R>>({
	// 		url, data
	// 	});
	// 	return response.body;
	// }

	/**
	 * 로그인
	 * @param credential
	 */
	const login = async (credential: Credential) => {
		try {
			const authResponse: AuthResponse = await doFetch({
				url: '/authenticate',
				data: credential,
			});
			dispatch({ type: 'LOGIN_SUCCESS', payload: authResponse });
		} catch(error) {
			alert('로그인 오류', (error as ExceptionResponse).message);
		}
	}

	const alert = (title: string, message: string) => {
		dispatch({ type: 'ALERT', payload: { title, message } });
	}

	const alertClose = () => {
		dispatch({ type: 'ALERT_CLOSE', payload: undefined });
	}

	return (
		<GlobalContext.Provider value={{
			...value,
			login,
			alert,
			alertClose
		}}>
			{children}
		</GlobalContext.Provider>
	)
}