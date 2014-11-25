public class RoomAgentInfo
{

  int row;
  int col;
  int id;
  String name;

  public RoomAgentInfo(int id, String name, int row, int col)
  {
    this.id = id;
    this.name = name;
    this.row = row;
    this.col = col;
  }

  public int getRow()
  {
    return row;
  }

  public void setRow(int row)
  {
    this.row = row;
  }

  public int getCol()
  {
    return col;
  }

  public void setCol(int col)
  {
    this.col = col;
  }

}
