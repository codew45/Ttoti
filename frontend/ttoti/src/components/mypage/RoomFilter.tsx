import styled from "styled-components";
import Calender from "./Calender";
import FilteredByFriend from "./FilteredByFriend";

interface RoomFilterProps {
  dateRange: [Date, Date];
  onDateChange: (dates: [Date, Date]) => void;
}

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
  font-weight: normal;
  font-size: 15px;
`;

const RoomFilter: React.FC<RoomFilterProps> = ({ dateRange, onDateChange }) => {
  return (
    <RoomFilterWrapper>
      <FilterWrapper>
        <Calender dateRange={dateRange} onDateChange={onDateChange} />
      </FilterWrapper>
      <FilterWrapper>
        <FilteredByFriend />
      </FilterWrapper>
    </RoomFilterWrapper>
  );
};

export default RoomFilter;
