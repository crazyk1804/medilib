import {ReactNode} from "react";
import {Credential} from "../context/global/TypeDefs";

/**
 * flux 패턴을 위한 action
 */
export interface ACTION {
	type: string
	payload: any
	meta?: any
	error?: boolean
}

/**
 * api 호출을 통해 서버에서 보내는 응답 형식
 */
export interface ServerResponse<T> {
	isSuccess: boolean,
	body: T
}

/**
 * 사용자 (User 는 예약어 같은 식으로도 사용이 많은 것 같아 Member 로 사용)
 */
export interface Member {
	identity: string
	username: string
	authorities: string[]
	hasRole: (role: string) => boolean
}

/**
 * 인증 결과(로그인 결과)
 */
export interface AuthResponse {
	member: Member
	token: string
}

/**
 * ReactNode 를 children 으로 갖는 타입(ContextProvider 등의 props 에 children 이 명시적이지 않아서...)
 */
export interface ReactParent {
	children?: ReactNode
}

/**
 * GlobalContext 의 상태 (이름이 말해준다)
 */
export interface GlobalContextState {
	loginMember?: Member,
	login: (credential: Credential) => void

	alertOpen: boolean,
	alert: (title: string, message: string) => void
	alertTitle: string
	alertMessage: string
	alertClose: () => void;
}

/**
 * 서버에서 오류 발생시 오는 메시지 타입
 */
export interface ExceptionResponse {
	/**
	 * 사용자에게 친화적인 정도
	 * @friendly - 그대로 보여줘도 좋을만한 메시지
	 * @notReally - 안될 것도 없는 것 같은데 기왕이면 꾸며주는게 좋은 메시지
	 * @notAtAll - 보여주는 건 니 맘인데 적당하진 않은 메시지 (개발자나 봐라 메시지)
	 */
	userFriendly: 'friendly' | 'notReally' | 'notAtAll'
	message: string
}