import React from 'react';
import { Pagination } from 'antd';
const PaginationControll = () => (
  <>
    <Pagination className='pagination'
      simple={{
        readOnly: true,
      }}
      defaultCurrent={1}
      total={50}
    />
  </>
);
export default PaginationControll;