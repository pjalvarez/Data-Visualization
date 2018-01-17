insert into source_type (id,name) values
(1, 'analogGauge'),
(2, 'lineChart'),
(3, 'barChart'),
(4, 'pieChart'),
(5, 'cards'),
(6, 'alarmWidget'),
(7, 'controlWidgets')
on duplicate key update id=id;