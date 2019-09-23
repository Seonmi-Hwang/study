# Android Database  
* 연락처 애플리케이션  

#### ⬛️ ArrayAdapter를 사용하는 것보다 CursorAdapter를 사용하는 것이 훨씬 편리.  
* **ArrayAdapter에 연결 시**  
> Cursor cursor = db.rawQuery("select \* from contact_table", null);  
> ArrayList<ContactDto> list = new ArrayList<ContactDto>();  
> while (cursor.moveToNext()) {
>	ContactDto dto = new ContactDto();  
> 	dto.setId(cursor.getInt(cursor.getColumnIndex("_id")));   
>	list.add(dto);  
> }  
> ArrayAdapter<ContactDto> adapter  
> 	= new ArrayAdapter<ContactDto>(this, android.R.layout.simple_list_item_1, list);  
>
> lvContacts.setAdapter(adapter);  

* **SimpleCursorAdapter에 연결 시**  
> cursor = db.rawQuery("select \* from contact_table", null);  
> 
> SimpleCursorAdapter adapter  
> 	= new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String["name"], new int[] {android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);  
> lvContacts.setAdapter(adapter);  

