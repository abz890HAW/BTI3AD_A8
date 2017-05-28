% Rohdaten
t_sizes = [10, 100, 1000, 10000, 100000, 1000000];
t_UPDATE_MIN = [8,90,930,9469,95001,951149];
t_UPDATE_MAX = [10,99,969,9555,95284,952130];
t_UPDATE_AVG = [9,93,945,9514,95135,951694];
t_SUM_MIN = [17,29,45,83,105,133];
t_SUM_MAX = [51,87,137,181,215,265];
t_SUM_AVG = [35,60,88,127,177,190];

% Plot: Komplexitätsentwicklung updateChildSums()
figure
loglog(t_sizes, t_UPDATE_MIN, t_sizes, t_UPDATE_AVG, t_sizes, t_UPDATE_MAX);
grid('on');
set(gca, 'YMinorGrid', 'Off');
%xlim([0 1E7]);
title('Komplexitätsentwicklung updateChildSums()');
legend('MIN', 'AVG', 'MAX', 'Location', 'northwest');
xlabel('Elemente [n]');
ylabel('Benötigte Instruktionen T(n)');
print('komplexitaetsentwicklung_updateChildSums','-depsc');
%close;

% Plot: Komplexitätsentwicklung limitSum()
figure
loglog(t_sizes, t_SUM_MIN, t_sizes, t_SUM_AVG, t_sizes, t_SUM_MAX);
grid('on');
set(gca, 'YMinorGrid', 'Off');
%xlim([0 1E7]);
title('Komplexitätsentwicklung limitSum()');
legend('MIN', 'AVG', 'MAX', 'Location', 'northwest');
xlabel('Elemente [n]');
ylabel('Benötigte Instruktionen T(n)');
print('komplexitaetsentwicklung_limitSum','-depsc');
%close;