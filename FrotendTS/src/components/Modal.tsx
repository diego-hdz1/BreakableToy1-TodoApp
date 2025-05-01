import type { FormProps } from 'antd';
import { Button, Checkbox, Form, Input, DatePicker, Select, Card, Space, Modal } from 'antd';
import dayjs from 'dayjs';
import {PORT} from '../constants';
import axios from "axios";

interface ModalProps{
    isModalOpen: boolean;
    fetchStats: () => void;
    setIsModalOpen: (data: boolean) => void;
    
}

const ModalComponent: React.FC<ModalProps> = ({
    isModalOpen,
    fetchStats,
    setIsModalOpen
}) => {
  const today = dayjs();
  const dateFormat = 'YYYY-MM-DD';

  type FieldType = {
    toDoName: string;
    toDoDate: string;
    priority: any;
    noToDoDate: any;
  };

  
  const onFinish: FormProps<FieldType>['onFinish'] = (values) => {

    const noDueDate = values.noToDoDate;
    let dueDateFinal = "";
    if(!noDueDate){
        dueDateFinal = dayjs(values.toDoDate).format(dateFormat); 
    }
    const toDoPriority = values.priority.value;
    const nowDate = new Date();
    let localDate = new Date(nowDate.getTime()-(nowDate.getTimezoneOffset() * 60000));
    const newToDo = {id: -1 ,text: values.toDoName, dueDate: dueDateFinal, status: true, doneDate: null, priority: toDoPriority, creationDate : localDate.toJSON()};

    //TO DO: Put a message that it was added correctly (Or there was an error)
    axios.post(`http://localhost:${PORT}/todos`, newToDo).then((response) => { 
        fetchStats();
        setIsModalOpen(false);
    });
    
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };
  
  const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

//   const handleDateChange = (date:any, dateString:any) =>{
//     setCurrentDate(dayjs(dayjs(dateString).format(dateFormat)));
//     console.log(date);
//   }

  return(
    <div>
        <Modal title="To Dos App" open={isModalOpen} onCancel={handleCancel} className="modal">
        <Space direction="vertical" size={16}>
        <Card title="Change this" className='show-card'>
        <Form
          name="basic"
          labelCol={{ span: 10 }}
          wrapperCol={{ span: 30 }}
          style={{ maxWidth: 800 }}
          initialValues={{ remember: true, nonStop: false }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
        >
          <Form.Item<FieldType>
            label="To Do"
            name={"toDoName"}
            rules={[{ required: true, message: 'Please input the To Do Name' }]}
          >

            <Input />
          </Form.Item>


          <Form.Item<FieldType>
            label="To Do Date"
            name={"toDoDate"}
          >
          <DatePicker minDate={today} />
          </Form.Item>

          <Form.Item name="noToDoDate" valuePropName="checked">
            <Checkbox>Do not use a to do date</Checkbox>
          </Form.Item>

          <Form.Item label="Priority: " name={"priority"} rules={[{ required: true, message: 'Please select a priority' }]}    >
          <Select
              labelInValue = {true}
              style={{ width: 120 }}
              options={[
              {
                  value: 1,
                  label: 'Low',
              },
              {
                  value: 2,
                  label: 'Medium',
              },
              {
                  value: 3,
                  label: 'High',
              },
              ]}
          />
          
          </Form.Item>

          <Form.Item label={null}>
            <Button type="primary" htmlType="submit" className='return-button'>
              Submit
            </Button>
          </Form.Item>

        </Form>
        </Card>
        </Space>
        </Modal>   
    </div>
  );
}

export default ModalComponent;