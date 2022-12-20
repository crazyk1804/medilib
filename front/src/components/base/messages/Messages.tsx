import React, {useState} from 'react';
import {Alert, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@mui/material";
import {MessageProps} from "./TypeDefs";
import Button from "@mui/material/Button";

export const AlertMessage = ({open, title, message}: MessageProps) => {
    const [opened, setOpened] = useState<boolean>(false);
    const [titleValue, setTitleValue] = useState<string>(title || '알림');
    const [messageValue, setMessageValue] = useState<string>(message || '표시할 메시지가 없습니다');

    return (
        <Dialog open={opened}
                onClose={() => setOpened(false)}
        >
            <DialogTitle>{titleValue}</DialogTitle>
            <DialogContent>
                <DialogContentText>{messageValue}</DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => setOpened(false)}>확인</Button>
            </DialogActions>
        </Dialog>
    )
}