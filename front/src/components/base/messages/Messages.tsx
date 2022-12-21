import React, {useState} from 'react';
import {Alert, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@mui/material";
import {MessageProps} from "./TypeDefs";
import Button from "@mui/material/Button";

export const AlertMessage = ({open, title, message, onClose}: MessageProps) => {
    return (
        /**
         * todo CHK01
         *  open, title, message 3가지를 한번에 변경하니 완전히 사라지기 전에
         *  글자가 먼저 변경되는게 보인다. 태그 자체를 없애면 페이드 아웃 효과가 사라지니
         *  title, message 2가지는 그대로 두는쪽으로 작업 진행함
         */
        // !open ? <></> :
            <Dialog open={open || false}
                    onClose={() => onClose && onClose()}
            >
                <DialogTitle>{title || '알림'}</DialogTitle>
                <DialogContent>
                    <DialogContentText>{message || '표시할 메시지가 없습니다'}</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => onClose && onClose()}>확인</Button>
                </DialogActions>
            </Dialog>

    )
}