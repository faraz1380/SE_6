# الگوهای طراحی

استراتژی حمل:
این بخش با استفاده از الگوی طراحی Strategy پیاده‌سازی شده است.
دو کلاس StandardShipping و ExpressShipping هزینه حمل بسته را بر اساس وزن بسته محاسبه می‌کنند.

وضعیت بسته:
این بخش با استفاده از الگوی طراحی State پیاده‌سازی شده است.
دو کلاس InTransitState و DeliveredState وضعیت بسته را مدیریت می‌کنند. زمانی که بسته به وضعیت "در حال حمل" می‌رود، پیامی نمایش داده می‌شود و زمانی که به وضعیت "تحویل شده" می‌رود، پیام مربوطه نمایش داده شده و برنامه خاتمه می‌یابد.

کلاس Package:
این کلاس وزن بسته، استراتژی حمل و وضعیت بسته را مدیریت می‌کند.
شامل متدهایی برای تنظیم استراتژی حمل و وضعیت بسته و محاسبه هزینه حمل می‌باشد.

## اینترفیس ShippingStrategy:
این اینترفیس یک متد calculateCost را تعریف می‌کند که باید توسط کلاس‌های مختلف استراتژی حمل پیاده‌سازی شود.
```java
public interface ShippingStrategy {
    double calculateCost(double weight);
}

```
## کلاس StandardShipping:
این کلاس پیاده‌سازی استراتژی حمل استاندارد است و متد calculateCost را بر اساس وزن بسته و ضریب 2.5 پیاده‌سازی می‌کند.
```java
public class StandardShipping implements ShippingStrategy {
    @Override
    public double calculateCost(double weight) {
        return weight * 2.5;
    }
}
```

## کلاس ExpressShipping:
این کلاس پیاده‌سازی استراتژی حمل سریع است و متد calculateCost را بر اساس وزن بسته و ضریب 3.5 پیاده‌سازی می‌کند.

```java
public class ExpressShipping implements ShippingStrategy {
    @Override
    public double calculateCost(double weight) {
        return weight * 3.5;
    }
}
```


## کلاس Package:
این کلاس شامل یک متغیر از نوع ShippingStrategy است که با استفاده از آن می‌توان استراتژی حمل را در زمان اجرا تغییر داد. همچنین متدی به نام setShippingStrategy دارد که استراتژی حمل جدید را تنظیم می‌کند و متد calculateCost که هزینه حمل را با استفاده از استراتژی فعلی محاسبه می‌کند.
```java
public class Package {
    private double weight;
    private ShippingStrategy shippingStrategy;
    private PackageState state;
    private double cost;

    public Package(double weight) {
        this.weight = weight;
        this.shippingStrategy = null;
        this.state = null;
        this.cost = 0;
    }

    public void setShippingStrategy(ShippingStrategy strategy) {
        this.shippingStrategy = strategy;
    }

    public void calculateCost() {
        if (this.shippingStrategy != null) {
            this.cost = this.shippingStrategy.calculateCost(this.weight);
        }
    }

    public double getCost() {
        return this.cost;
    }

    ...
}
```

## رویکرد TDD

### تست testCreateClass()

ابتدا تست را می‌نویسیم که بررسی می‌کند آیا شیء Package به درستی ایجاد شده است، وزن آن به درستی تنظیم شده است، و وضعیت اولیه آن null است. همچنین، هزینه اولیه باید 0.0 باشد.
```java
    @Test
    public void testCreateClass() {
        double weight = 10.0;
        Package pkg = new Package(weight);
        assertNotNull(pkg, "Package object should be created successfully.");
        assertEquals(weight, pkg.getWeight(), "Package weight should be set correctly.");
        assertNull(pkg.getState(), "Initial state should be null.");
        assertEquals(0.0, pkg.getCost(), "Initial cost should be 0.0.");
    }
```
کد مربوط به کلاس Package را می‌نویسیم تا این تست‌ها موفق شوند.

### تست testSetShipping()
تستی را می‌نویسیم که بررسی کند آیا تنظیم استراتژی حمل استاندارد و سریع به درستی انجام می‌شود و هزینه حمل به درستی محاسبه می‌شود.
```java
    @Test
    public void testSetShipping() {
        double weight = 10.0;
        Package pkg = new Package(weight);
        ShippingStrategy standardShipping = new StandardShipping();
        ShippingStrategy expressShipping = new ExpressShipping();

        pkg.setShippingStrategy(standardShipping);
        pkg.calculateCost();
        assertEquals(weight * 2.5, pkg.getCost(), 0.01, "Standard shipping cost should be calculated correctly.");

        pkg.setShippingStrategy(expressShipping);
        pkg.calculateCost();
        assertEquals(weight * 3.5, pkg.getCost(), 0.01, "Express shipping cost should be calculated correctly.");
    }
```
کد مربوط به متدهای setShippingStrategy و calculateCost را می‌نویسیم. 

### تست testSendPackage()

تستی را می‌نویسیم که بررسی کند آیا تنظیم وضعیت بسته به InTransitState و DeliveredState به درستی انجام می‌شود.
```java
    @Test
    public void testSendPackage() {
        double weight = 10.0;
        Package pkg = new Package(weight);
        PackageState inTransitState = new InTransitState();
        PackageState deliveredState = new DeliveredState();

        pkg.setState(inTransitState);
        assertTrue(pkg.getState() instanceof InTransitState, "Package state should be InTransitState.");

        pkg.setState(deliveredState);
        assertTrue(pkg.getState() instanceof DeliveredState, "Package state should be DeliveredState.");
    }
```
کد مربوط به متد setState را می‌نویسیم.


نتیجه کد در حال اجرا هم در تصویر زیر آمده:

![image1](https://github.com/faraz1380/SE_6/assets/63359673/292f3d90-0e7f-45cc-954b-82119ecf97eb)




# پرسش‌ها
## پرسش اول
سه دسته‌ی الگوهای طراحی معروف در کتاب GoF عبارتند از:

الگوهای سازنده (Creational Patterns):
این الگوها مسئول ایجاد اشیاء به شیوه‌ای هستند که مناسب موقعیت باشد. آن‌ها فرایند ایجاد شیء را با انتزاع انجام می‌دهند و از وابستگی مستقیم به کلاس‌های خاص جلوگیری می‌کنند، که در نتیجه انعطاف‌پذیری و باز استفاده‌پذیری کد را افزایش می‌دهند.
مثال: singleton, factory method.
الگوهای ساختاری (Structural Patterns):
این الگوها به ترکیب کلاس‌ها و اشیاء برای ایجاد ساختارهای بزرگ‌تر و پیچیده‌تر کمک می‌کنند. هدف این الگوها ساده‌سازی طراحی از طریق استفاده مجدد از رابط‌ها و پیاده‌سازی‌های مختلف برای کاهش پیچیدگی و افزایش کارایی سیستم است.
مثال: adapter, composite
الگوهای رفتاری (Behavioral Patterns):
این الگوها با الگوریتم‌ها و تخصیص مسئولیت بین اشیاء سروکار دارند. آن‌ها تعاملات و همکاری بین اشیاء را تعریف می‌کنند و به توزیع مسئولیت‌ها به شیوه‌ای مناسب و قابل پیش‌بینی کمک می‌کنند.
مثال: strategy, observer

## پرسش دوم
در این آزمایش از دو الگوی state و strategy استفاده شده که از دسته‌ی رفتاری هستند چون تمرکز بر الگوریتم‌ها و تخصیص مسئولیت بین اشیاء دارند.

پترن state:  این الگو رفتار شیء را به حالت‌های مختلفی تقسیم می‌کند و مدیریت این تغییرات را بر عهده دارد. به عبارتی، State pattern رفتار شیء را در هنگام تغییر حالت به روش‌های مختلفی کنترل می‌کند، که این موضوع به تعاملات و رفتارهای شیء مربوط می‌شود.

پترن strategy: این الگو رفتار یک شیء را بر اساس استراتژی انتخابی تغییر می‌دهد. به عبارتی، Strategy pattern امکان انتخاب و تغییر الگوریتم (رفتار) را در زمان اجرا فراهم می‌کند، که این موضوع به رفتار شیء و نحوه‌ی تعاملات آن مربوط می‌شود.

## پرسش سوم
ابا توجه به اینکه در هر اجرا تنها یک بسته وجود دارد و هیچ بسته‌ی دیگری وجود نخواهد داشت، الگوی طراحی Singleton برای ایجاد و مدیریت این بسته مناسب است. الگوی Singleton تضمین می‌کند که تنها یک نمونه از کلاس ایجاد شود و دسترسی سراسری به آن نمونه فراهم شود.
علت انتخاب الگوی Singleton:

کنترل ایجاد شیء: الگوی Singleton تضمین می‌کند که تنها یک نمونه از کلاس Package ایجاد می‌شود. این موضوع با نیاز شما که فقط یک بسته در هر اجرا داریم، کاملاً سازگار است.

دسترسی سراسری: الگوی Singleton دسترسی سراسری به نمونه‌ی کلاس فراهم می‌کند. بدین معنی که در هر جای برنامه می‌توان به بسته دسترسی داشت.

مدیریت ساده: با استفاده از Singleton، مدیریت ایجاد و دسترسی به بسته بسیار ساده و بدون پیچیدگی خواهد بود.

نحوه تحقق الگوی Singleton:

برای پیاده‌سازی الگوی Singleton در کلاس Package مراحل زیر را انجام می‌دهیم:

ایجاد یک متغیر استاتیک خصوصی: این متغیر تنها نمونه‌ای از کلاس را نگه‌داری می‌کند.

ایجاد یک سازنده خصوصی: سازنده کلاس Package را خصوصی می‌کنیم تا از ایجاد نمونه‌های متعدد جلوگیری کنیم.

ایجاد یک متد عمومی استاتیک برای دسترسی به نمونه: این متد نمونه‌ی Singleton را برمی‌گرداند و در صورت نیاز آن را ایجاد می‌کند.


## پرسش چهارم
1. Single Responsibility Principle (SRP)

    تحقق: Singleton معمولاً یک وظیفه واحد دارد، یعنی مدیریت یک نمونه واحد از کلاس.
    عدم تحقق: در صورتی که Singleton بیش از حد وظایف مختلفی را بر عهده بگیرد، اصل SRP نقض می‌شود.

2. Open/Closed Principle (OCP)

    عدم تحقق: کلاس Singleton معمولاً بسته برای تغییر و باز برای توسعه نیست. تغییر در عملکرد Singleton نیاز به تغییر در کلاس دارد که این اصل را نقض می‌کند.

3. Liskov Substitution Principle (LSP)

    تحقق: در صورت استفاده از یک اینترفیس یا کلاس پایه، Singleton می‌تواند این اصل را تحقق بخشد، زیرا هر گونه زیرکلاسی از Singleton می‌تواند جایگزین آن شود.
    عدم تحقق: اگر Singleton به صورت مستقیم از اینترفیس یا کلاس پایه استفاده نکند، ممکن است این اصل نقض شود.

4. Interface Segregation Principle (ISP)

    تحقق: اگر Singleton تنها وظایف خاص خود را انجام دهد و از اینترفیس‌های تخصصی استفاده کند، این اصل رعایت می‌شود.
    عدم تحقق: در صورتی که Singleton وظایف متعددی را بر عهده بگیرد و از اینترفیس‌های جامع و عمومی استفاده کند، این اصل نقض می‌شود.

5. Dependency Inversion Principle (DIP)

    عدم تحقق: Singleton به دلیل وابستگی به نمونه‌ی خود و عدم امکان تزریق وابستگی‌ها (Dependency Injection) معمولاً این اصل را نقض می‌کند.
    تحقق: در صورتی که Singleton از اینترفیس‌ها برای وابستگی‌های خود استفاده کند و از تزریق وابستگی‌ها بهره ببرد، می‌تواند این اصل را تحقق بخشد.
