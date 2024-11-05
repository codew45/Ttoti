import styled from "styled-components";
import Calender from "./Calender";
import FilteredByFriend from "./FilteredByFriend";

const RoomFilterWrapper = styled.div`
  height: 50px;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
`;

const FilterWrapper = styled.div`
  width: 150px;
  height: 40px;
  background-color: white;
  border-radius: 10px;
  border: 1px solid black;
  font-family: 'LINESeed';
  font-weight: 300;
  font-size: 15px;
`;

const RoomFilter = () => {
  return <RoomFilterWrapper>
    <FilterWrapper>
      <Calender />
    </FilterWrapper>
    <FilterWrapper>
      <FilteredByFriend />
    </FilterWrapper>
  </RoomFilterWrapper>;
}

export default RoomFilter