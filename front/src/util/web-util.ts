import {ExceptionResponse} from "../general/GeneralTypes";

const serverAddress = 'http://localhost:8080';

export const getHeaders = (fetchParam: FetchParam<any>): HeadersInit | undefined => {
	let headers = {
		'Accept': 'application/json',
		'Content-Type': 'application/json'
	};
	if(fetchParam.contentType && fetchParam.contentType !== 'multipart/form-data')
		headers['Content-Type'] = fetchParam.contentType

	const token = localStorage.getItem('token');
	if(!token) return headers;
	return Object.assign(headers, {'Authorization': `Bearer ${token}`});
};

export interface FetchParam<P> {
	url: string,
	contentType?: string
	method?: string,
	headers?: {}
	data?: P,
}

export const doFetch = <P, R>(fetchParam: FetchParam<P>): Promise<R> => {
	let body: any = null;
	if(fetchParam.contentType === 'multipart/form-data')
		body = fetchParam.data;
	else
		body = (fetchParam.data) ? JSON.stringify(fetchParam.data) : null;

	return new Promise<R>((resolve, reject) => {
		fetch(`${serverAddress}${fetchParam.url}`, {
			method: fetchParam.method || 'POST',
			headers: getHeaders(fetchParam),
			body: body,
		}).then(response => {
			/*response check*/
			if(!response) {
				reject(new Error(`How can there's NO RESPONSE on request ${ fetchParam.url }`));
			}

			console.log('response status', response.status)
			if(response.status < 200 || response.status > 300)
				throw response;

			// throw new Error(`response status: ${ response.status }: ${response.statusText}`);
			return response.text();
		}).then(bodyString => {
			try {
				return JSON.parse(bodyString);
			} catch(e) {
				throw e;
			}
		}).then((json: R) => {
			if(!json) return;
			resolve(json);
		}).catch(errorResponse => {
			errorResponse.text()
				.then((txt: string) => {
					try {
						const json = JSON.parse(txt);
						const body = json.body as ExceptionResponse;
						reject(body);
						// console.log('error body', body.message);
					} catch(error) {
						reject(txt);
						// console.log('error txt', txt);
					}
				})
			// const responseText = errorResponse
			// console.log('error type: ', typeof(errorResponse), errorResponse);
			// // reject(error);
		}).catch(txt => {
			console.log('error then response text', txt);
		});
	});
};

export const doFetchDown = (atchFileId: number, fileSn: number, filename: string): Promise<Blob> => {
	const token = localStorage.getItem('token');
	return new Promise<Blob>((resolve, reject) => {
		fetch(`${serverAddress}/api/file/download`, {
			method: 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			},
			body: JSON.stringify({atchFileId, fileSn})
		})
		.then(response => response.blob())
		.then(blob => {
			const url = window.URL.createObjectURL(blob);
			const a = document.createElement('a');
			a.href = url;
			a.download = filename;
			document.body.appendChild(a);
			a.click();
			a.remove();
		})
	})
}

// export const api = <P, R>(fetchParam: FetchParam<P>): Promise<R | void> => {
// 	return new Promise<R | void>((resolve, reject) => {
// 		doFetch<P, BackResponse<R>>(fetchParam).then(result => {
// 			try {
// 				if (!result.success) {
// 					reject({
// 						code: result.code,
// 						message: result.message
// 					});
// 					return;
// 				}
//
// 				if (result.data!=null && result.data!=undefined)
// 					resolve(result.data);
// 				else
// 					resolve()
// 			} catch(error) {
// 				reject({
// 					code: 'XXX',
// 					message: 'unknown error',
// 					error: error
// 				});
// 			}
// 		}).catch(error => {
// 			reject({
// 				code: 'XXX',
// 				message: 'unknown error',
// 				error: error
// 			});
// 		});
// 	});
// };

// export const createGetUrl = (baseUrl: string, params: any, filter?: string[]): string => {
// 	let isFirstParam = true;
// 	return Object.keys(params).reduce((result, key, ix) => {
// 		if(filter && filter.indexOf(key)>=0) return result;
// 		const paramValue: string | Array<string> = params[key] || '';
// 		const value = Array.isArray(paramValue) ? _.join(paramValue, ',') : paramValue;
//
// 		const combined = `${result}${isFirstParam ? '?' :
// 							value? '&' : ''}${value? `${key}=${value}` : ''}`;
// 		// const combined = `${result}${isFirstParam ? '?' : '&'}${key}=${paramValue}`;
// 		isFirstParam = false;
// 		return combined;
// 	}, baseUrl)
// };