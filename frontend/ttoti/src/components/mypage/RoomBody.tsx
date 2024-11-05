import { useState } from "react";
import RoomFilter from "./RoomFilter";
import RoomList from "./RoomList";
import styled from "styled-components";

const RoomWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: -10px;
`;

const RoomBody = () => {
  const [dateRange, setDateRange] = useState<[Date, Date]>([
    new Date(),
    new Date(),
  ]); // 초기화 시 [Date, Date]로 지정

  return (
    <RoomWrapper>
      <RoomFilter dateRange={dateRange} onDateChange={setDateRange} />
      <RoomList dateRange={dateRange} />
    </RoomWrapper>
  );
};

export default RoomBody;
